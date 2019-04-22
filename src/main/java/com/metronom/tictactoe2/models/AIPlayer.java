package com.metronom.tictactoe2.models;

import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.metronom.tictactoe2.config.Params;
import com.metronom.tictactoe2.console.ConsoleHandler;
import com.metronom.tictactoe2.engine.PlayEngine;

/** AIPlayer using Minimax algorithm */
public class AIPlayer extends Player {

	private PlayField field = PlayField.getInstance();
	private boolean firstMove = true;

	AIPlayer(String name, char symbol, boolean aiSupport, ConsoleHandler handler, PlayEngine engine) {
		super(name, symbol, aiSupport, handler, engine);
	}

	public Map<Player, Integer> getCurrentScore(final PlayField field) {
		int length = field.getPlayFieldLength();
		PlayEngine engine = getEngine();
		Params params = engine.getParams();
		Map<Player, Integer> scores = new HashMap<>();
		for (Player player : engine.getPlayers()) {
			if (player != this) {
				char symbol = player.getSymbol();
				for (int row = 0; row < length; row++) {
					for (int column = 0; column < length; column++) {
						Cell cell = new Cell(row, column);
						Optional<Character> cellValue = field.getCell(row, column);
						if (cellValue.isPresent()) {
							if (cellValue.get().equals(player.getSymbol())) {
								int totalScore = field.calculateHorizontalScore(cell)
										+ field.calculateVerticalScore(cell) + field.calculateDiagonal1Score(cell)
										+ field.calculateDiagonal2Score(cell);
								scores.put(player, totalScore);
								break;
							}
						}
					}
				}
			}
		}
		return scores;
	}

	int[] move(Player opponent) {
		Character[][] table = field.getTable().clone();
		int[] result = minimax(2, this, opponent, table); // depth, max turn
		return new int[] { result[1], result[2] }; // row, col
	}

	/**
	 * Recursive minimax at level of depth for either maximizing or minimizing
	 * player. Return int[3] of {score, row, col}
	 */
	private int[] minimax(int depth, Player player1, Player player2, Character[][] table) {
		// Generate possible next moves in a List of int[2] of {row, col}.
		List<int[]> nextMoves = generateMoves(table, player1);

		// mySeed is maximizing; while oppSeed is minimizing
		int bestScore = (player1.getSymbol() == getSymbol()) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int currentScore;
		int bestRow = -1;
		int bestCol = -1;

		// if (nextMoves.isEmpty() || depth == 0) {
		if (nextMoves.isEmpty() || depth == 0) {
			// Gameover or depth reached, evaluate score
			bestScore = field.evaluate(table, player1.getSymbol());
		} else {
			for (int[] move : nextMoves) {
				// Try this move for the current "player"
				table[move[0]][move[1]] = player1.getSymbol();
				if (player1.getSymbol() == getSymbol()) { // mySeed (computer) is maximizing player
					currentScore = minimax(depth - 1, player2, player1, table)[0];
					if (currentScore > bestScore) {
						bestScore = currentScore;
						bestRow = move[0];
						bestCol = move[1];
					}
				} else { // oppSeed is minimizing player
					currentScore = minimax(depth - 1, player2, player1, table)[0];
					if (currentScore < bestScore) {
						bestScore = currentScore;
						bestRow = move[0];
						bestCol = move[1];
					}
				}
				// Undo move
				table[move[0]][move[1]] = null;
			}
		}
		return new int[] { bestScore, bestRow, bestCol };
	}

	/**
	 * Find all valid next moves. Return List of moves in int[2] of {row, col} or
	 * empty list if gameover
	 */
	private List<int[]> generateMoves(Character[][] field, Player player) {
		List<int[]> nextMoves = new ArrayList<int[]>(); // allocate List

		// If gameover, i.e., no next move
		if (hasWon(this, field) || hasWon(player, field)) {
			return nextMoves; // return empty list
		}

		// Search for empty cells and add to the List
		for (int row = 0; row < this.getEngine().getParams().getPlayFieldSize(); row++) {
			for (int col = 0; col < this.getEngine().getParams().getPlayFieldSize(); col++) {
				if (field[row][col] == null) {
					nextMoves.add(new int[] { row, col });
				}
			}
		}
		return nextMoves;
	}

	/** Returns true if thePlayer wins */
	private boolean hasWon(Player thePlayer, Character[][] table) {
		int playfieldLength = field.getPlayFieldLength();
		List<List<Integer>> blankTable = field.getBlankTable();
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < playfieldLength; j++) {
				if (table[i][j] != null) {
					if (table[i][j] == thePlayer.getSymbol()) {
						blankTable.get(i).set(j, 1);
					}
				}
			}
		}
		String pattern = blankTable.stream().flatMap(Collection::stream).map(n -> n.toString())
				.collect(Collectors.joining(""));
		if (field.getWinningPatterns().contains(new BigInteger(pattern, 2))) {
			return true;
		}
		return false;
	}

	@Override
	public Optional<Cell> getNextMove(final PlayField field) {

		// Get Player with Highest Score

		List<int[]> moves = new ArrayList<int[]>();
		Random rand = new Random();

		if (firstMove) {
			// Move any corner or the middle position
			firstMove = false;
			return Optional.ofNullable(new Cell(rand.nextInt(field.getPlayFieldLength() - 1),
					rand.nextInt(field.getPlayFieldLength() - 1)));
		}

		for (Player player : getEngine().getPlayers()) {
			if (player.getSymbol() != getSymbol()) {
				int[] move = move(player);
				moves.add(move);
			}
		}

		int[] randomElement = moves.get(rand.nextInt(moves.size()));
		return Optional.ofNullable(new Cell(randomElement[0], randomElement[1]));

	}
}
