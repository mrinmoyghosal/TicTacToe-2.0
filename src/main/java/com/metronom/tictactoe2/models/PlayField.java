package com.metronom.tictactoe2.models;

import com.metronom.tictactoe2.console.ConsoleMessage;
import com.metronom.tictactoe2.exceptions.InvalidCellException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayField {

	private static PlayField instance = new PlayField();

	private Character[][] table;
	private int freeRoomCount;
	private int playFieldSize;
	private List<BigInteger> winningPatterns;

	// Block Initialization
	private PlayField() {
	}

	/*
	 * GETTER AND SETTERS
	 * 
	 */

	public static PlayField getInstance() {
		return instance;
	}

	public Character[][] getTable() {
		return table;
	}

	private void setWinningPatterns(List<BigInteger> pat) {
		this.winningPatterns = pat;

	}

	public List<BigInteger> getWinningPatterns() {
		return winningPatterns;
	}

	/**
	 * Gives the free room count in the board.
	 *
	 * @return {@code freeRoomCount}
	 */
	public int getFreeRoomCount() {
		return freeRoomCount;
	}

	/**
	 * Gives the board length.
	 *
	 * @return {@code playFieldSize}
	 */
	public int getPlayFieldLength() {
		return playFieldSize;
	}

	List<List<Integer>> getBlankTable() {
		List<List<Integer>> pat = new ArrayList<>();
		for (int i = 0; i < playFieldSize; i++) {
			List<Integer> rowlist = new ArrayList<>();
			for (int j = 0; j < playFieldSize; j++) {
				rowlist.add(0);
			}
			pat.add(rowlist);
		}
		return pat;
	}

	/**
	 * Puts a character in the {@code cell} location of the board.
	 *
	 * @param value the character to put on board
	 * @param cell  location to put the character
	 * @throws InvalidCellException if the {@code cell} is not in the boards length
	 *                              or the cell is already full.
	 */
	public void put(final Character value, final Cell cell) throws InvalidCellException {
		if (!isInRange(cell.getRow(), cell.getColumn()))
			throw new InvalidCellException(
					String.format(ConsoleMessage.INVALID_POINT.getMessageText(), playFieldSize, playFieldSize));

		if (table[cell.getRow()][cell.getColumn()] != null)
			throw new InvalidCellException(ConsoleMessage.CELL_ALREADY_FULL.getMessageText());

		table[cell.getRow()][cell.getColumn()] = value;
		freeRoomCount--;
	}

	/**
	 * Initializes the board by setting the {@code playFieldSize} and instantiating
	 * the board {@code table} and setting the {@code freeRoomCount}.
	 *
	 * @param playFieldSize length of the board
	 */
	public void init(int playFieldSize) {
		this.playFieldSize = playFieldSize;
		this.table = new Character[playFieldSize][playFieldSize];
		this.freeRoomCount = playFieldSize * playFieldSize;
	}

	/*
	 * Creates all possible winning patterns in binary 1,0 -
	 * 
	 * | x | x | x | | | | | => one example of winning pattern (111000000) | | | |
	 * 
	 * | x | | | | x | | | => one example of winning pattern (100100100) | x | | |
	 * 
	 * 
	 */
	public void populateWinningPatterns() {

		List<String> patterns = new ArrayList<String>();

		// populate row patterns
		for (int i = 0; i < playFieldSize; i++) {
			List<List<Integer>> table = getBlankTable();
			for (int j = 0; j < playFieldSize; j++) {
				table.get(i).set(j, 1);
			}
			String pattern = table.stream().flatMap(Collection::stream).map(n -> n.toString())
					.collect(Collectors.joining(""));
			patterns.add(pattern);
		}

		// populate column patterns
		for (int i = 0; i < playFieldSize; i++) {
			List<List<Integer>> table = getBlankTable();
			for (int j = 0; j < playFieldSize; j++) {
				table.get(j).set(i, 1);
			}
			String pattern = table.stream().flatMap(Collection::stream).map(n -> n.toString())
					.collect(Collectors.joining(""));
			patterns.add(pattern);
		}

		// populate diagonal pattern
		List<List<Integer>> diagonalTable = getBlankTable();
		List<List<Integer>> diagonalAlternateTable = getBlankTable();
		for (int j = 0; j < playFieldSize; j++) {
			diagonalTable.get(j).set(j, 1);
			diagonalAlternateTable.get(j).set((playFieldSize - 1) - j, 1);
		}
		String diagonalPattern = diagonalTable.stream().flatMap(Collection::stream).map(n -> n.toString())
				.collect(Collectors.joining(""));
		String alternatediagonalPattern = diagonalAlternateTable.stream().flatMap(Collection::stream)
				.map(n -> n.toString()).collect(Collectors.joining(""));
		patterns.add(diagonalPattern);
		patterns.add(alternatediagonalPattern);

		List<BigInteger> pat = patterns.stream().map(n -> new BigInteger(n, 2)).collect(Collectors.toList());

		this.setWinningPatterns(pat);

	}

	/**
	 * Return heuristic score for each row columns and diagonal
	 * 
	 * @param table
	 * @param mySymbol
	 * @return {@link int} score
	 */
	public int evaluate(Character[][] table, char mySymbol) {
		int rowScore = 0;

		// evaluate row scores
		for (int i = 0; i < playFieldSize; i++) {
			boolean first = true;
			for (int j = 0; j < playFieldSize; j++) {
				if (first) {
					if (table[i][j] == null) {
						rowScore = -1;
					} else if (table[i][j] == mySymbol) {
						rowScore = 1;
					}
					first = false;
				} else {
					if (table[i][j] != null) {

						if (table[i][j] == mySymbol) {
							if (rowScore >= 1) {
								rowScore *= 10;
							} else if (rowScore < 0) {
								rowScore = 0;
							} else {
								rowScore = 1;
							}
						} else {
							if (rowScore < 0) {
								rowScore *= 10;
							} else if (rowScore > 1) {
								rowScore = 0;
							} else {
								rowScore = -1;
							}
						}
					}
				}
			}

		}

		// evaluate column scores
		int colScore = 0;
		for (int i = 0; i < playFieldSize; i++) {
			boolean first = true;
			for (int j = 0; j < playFieldSize; j++) {
				if (first) {
					if (table[j][i] == null) {
						colScore = -1;
					} else if (table[j][i] == mySymbol) {
						colScore = 1;
					}
					first = false;
				} else {
					if (table[j][i] == null) {
						colScore = -1;
					} else if (table[j][i] == mySymbol) {
						if (colScore > 0) {
							colScore *= 10;
						} else if (colScore < 0) {
							colScore = 0;
						} else {
							colScore = 1;
						}
					} else if (table[j][i] != null) {
						if (colScore < 0) {
							colScore *= 10;
						} else if (colScore > 1) {
							colScore = 0;
						} else {
							colScore = -1;
						}
					}
				}
			}

		}

		int diagonalScore = 0;
		int alternateDiagonalScore = 0;
		boolean diagonalFirst = true;
		for (int j = 0; j < playFieldSize; j++) {
			if (diagonalFirst) {
				if (table[j][j] == null) {
					diagonalScore = -1;
				} else if (table[j][j] == mySymbol) {
					diagonalScore = 1;
				}
				diagonalFirst = false;
			} else {
				if (table[j][j] == null) {
					diagonalScore = -1;
				} else if (table[j][j] == mySymbol) {
					if (diagonalScore > 0) {
						diagonalScore *= 10;
					} else if (diagonalScore < 0) {
						diagonalScore = 0;
					} else {
						diagonalScore = 1;
					}
				} else if (table[j][j] != null) {
					if (diagonalScore < 0) {
						diagonalScore *= 10;
					} else if (diagonalScore > 1) {
						diagonalScore = 0;
					} else {
						diagonalScore = -1;
					}
				}
			}

		}
		boolean alternateDiagonalFirst = true;
		for (int j = 0; j < playFieldSize; j++) {
			if (alternateDiagonalFirst) {
				if (table[j][(playFieldSize - 1) - j] == null) {
					alternateDiagonalScore = -1;
				} else if (table[j][(playFieldSize - 1) - j] == mySymbol) {
					alternateDiagonalScore = 1;
				}
				alternateDiagonalFirst = false;
			} else {
				if (table[j][(playFieldSize - 1) - j] == null) {
					alternateDiagonalScore = -1;
				} else if (table[j][(playFieldSize - 1) - j] == mySymbol) {
					if (alternateDiagonalScore > 0) {
						alternateDiagonalScore *= 10;
					} else if (alternateDiagonalScore < 0) {
						alternateDiagonalScore = 0;
					} else {
						alternateDiagonalScore = 1;
					}
				} else if (table[j][(playFieldSize - 1) - j] != null) {
					if (alternateDiagonalScore < 0) {
						alternateDiagonalScore *= 10;
					} else if (alternateDiagonalScore > 1) {
						alternateDiagonalScore = 0;
					} else {
						alternateDiagonalScore = -1;
					}
				}
			}

		}

		int finalScore = rowScore + colScore + diagonalScore + alternateDiagonalScore;
		return finalScore;
	}

	/**
	 * Calculates the horizontal score of the given cell. Assume that we have a
	 * {@code x} at the location {@code {0, 0}} and we have another {@code x} at the
	 * location {@code {0, 1}}. In this case, this method will return {@code 2} for
	 * both cells.
	 *
	 * @param cell the location of the board that we want to calculate it's
	 *             horizontal score.
	 * @return score of the given cell.
	 */
	public int calculateHorizontalScore(final Cell cell) {
		return calculateScore(cell, 0, -1, 0, 1);
	}

	/**
	 * Calculates the vertical score of the given cell. Assume that we have a
	 * {@code x} at the location {@code {0, 0}} and we have another {@code x} at the
	 * location {@code {1, 0}}. In this case, this method will return {@code 2} for
	 * both cells.
	 *
	 * @param cell the location of the board that we want to calculate it's vertical
	 *             score.
	 * @return score of the given cell.
	 */
	public int calculateVerticalScore(final Cell cell) {
		return calculateScore(cell, -1, 0, 1, 0);
	}

	/**
	 * Calculates the diagonal1 score of the given cell. Assume that we have a
	 * {@code x} at the location {@code {0, 0}} and we have another {@code x} at the
	 * location {@code {1, 1}}. In this case, this method will return {@code 2} for
	 * both cells.
	 *
	 * @param cell the location of the board that we want to calculate it's
	 *             diagonal1 score.
	 * @return score of the given cell.
	 */
	public int calculateDiagonal1Score(final Cell cell) {
		return calculateScore(cell, -1, -1, 1, 1);
	}

	/**
	 * Calculates the diagonal2 score of the given cell. Assume that we have a
	 * {@code x} at the location {@code {0, 1}} and we have another {@code x} at the
	 * location {@code {1, 0}}. In this case, this method will return {@code 2} for
	 * both cells.
	 *
	 * @param cell the location of the board that we want to calculate it's
	 *             diagonal2 score.
	 * @return score of the given cell.
	 */
	public int calculateDiagonal2Score(final Cell cell) {
		return calculateScore(cell, -1, 1, 1, -1);
	}

	/**
	 * Gives the value of the given cell as an {@code Optional<Character>}. This
	 * method will return {@code Optional.empty} if the location is empty or if the
	 * given cell is not in the range of the board.
	 *
	 * @param row    row number of the board
	 * @param column column number of the board
	 * @return {@code Optional<Character>}
	 */
	public Optional<Character> getCell(final int row, final int column) {
		if (isInRange(row, column))
			return Optional.ofNullable(table[row][column]);

		return Optional.empty();
	}

	/**
	 * Indicates that, the given cell is inside the board or not.
	 *
	 * @param row    row number
	 * @param column column number
	 * @return {@code true} if the cell is inside the board and otherwise
	 *         {@code false}
	 */
	private boolean isInRange(int row, int column) {
		return row >= 0 && column >= 0 && row < playFieldSize && column < playFieldSize;
	}

	/**
	 * This is the main method for calculating the score of a cell in any direction.
	 * We can specify the direction using the input parameters. <br/>
	 * <br/>
	 * We have 4 {@code int} parameters that first two parameters specify that when
	 * we want to find the start of the line, in which direction we have to move.
	 * The next two parameters specify that when we want to find the end of the
	 * line, in which direction we have to move.
	 *
	 * @param cell           the base cell which we want to calculate the score
	 *                       based on that
	 * @param startRowInc    the amount of row increment when we want to move toward
	 *                       the start of the line
	 * @param startColumnInc the amount of column increment when we want to move
	 *                       toward the start of the line
	 * @param endRowInc      the amount of row increment when we want to move toward
	 *                       the end of the line
	 * @param endColumnInc   the amount of column increment when we want to move
	 *                       toward the end of the line
	 * @return a number equals or greater than 0 that indicates the score of the
	 *         given cell using the given direction.
	 */
	private int calculateScore(final Cell cell, final int startRowInc, final int startColumnInc, final int endRowInc,
			final int endColumnInc) {
		int startRow = cell.getRow();
		int startColumn = cell.getColumn();
		int endRow = cell.getRow();
		int endColumn = cell.getColumn();

		final Optional<Character> cellValue = getCell(cell.getRow(), cell.getColumn());

		if (cellValue.isPresent()) {
			// Finding the start of the line
			while (isInRange(startRow, startColumn)) {
				if (cellValue.equals(getCell(startRow + startRowInc, startColumn + startColumnInc))) {
					startRow += startRowInc;
					startColumn += startColumnInc;
				} else
					break;
			}

			// Finding the end of the line
			while (isInRange(endRow, endColumn)) {
				if (cellValue.equals(getCell(endRow + endRowInc, endColumn + endColumnInc))) {
					endRow += endRowInc;
					endColumn += endColumnInc;
				} else
					break;
			}

			return Math.max(endRow - startRow + 1, endColumn - startColumn + 1);
		}

		return 0;
	}

}
