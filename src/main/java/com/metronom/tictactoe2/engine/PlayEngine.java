package com.metronom.tictactoe2.engine;

import com.metronom.tictactoe2.models.PlayRuntimeStatus;
import com.metronom.tictactoe2.models.PlayField;
import com.metronom.tictactoe2.models.Cell;
import com.metronom.tictactoe2.models.Player;
import com.metronom.tictactoe2.models.PlayerBuilder;
import com.metronom.tictactoe2.exceptions.InvalidCellException;
import com.metronom.tictactoe2.config.Params;
import com.metronom.tictactoe2.console.ConsoleHandler;

import java.util.Optional;

public class PlayEngine {

	private int turn;
	private int winScore;

	private Params params;
	private PlayField field = PlayField.getInstance();
	private Player[] players;
	private Player winner;
	private PlayRuntimeStatus status;
	ConsoleHandler handler = ConsoleHandler.getInstance();

	/**
	 * Instantiates and initializes the game. This is the main game controller.
	 *
	 * @param config instance of {@link Params}
	 */
	public PlayEngine(final Params params) {
		this.params = params;
		this.winScore = params.getPlayFieldSize();

		field.init(params.getPlayFieldSize());
		field.populateWinningPatterns();

		this.players = new Player[3];

		this.players[0] = new PlayerBuilder().withName("AI Player").withSymbol(params.getAIPlayerSymbol())
				.withAISupport().withConsoleHandler(handler).withEngineContext(this).build();

		this.players[1] = new PlayerBuilder().withName("Player1").withSymbol(params.getPlayerOneSymbol())
				.withConsoleHandler(handler).withEngineContext(this).build();

		this.players[2] = new PlayerBuilder().withName("Player2").withSymbol(params.getPlayerTwoSymbol())
				.withConsoleHandler(handler).withEngineContext(this).build();

		this.turn = 0;
		this.status = PlayRuntimeStatus.ON;
	}

	/**
	 * \ Gives the status of the game. The status type is {@link PlayRuntimeStatus}.
	 *
	 * @return {@link PlayRuntimeStatus} that indicates the final status of the game
	 *         at the time. This can be {@code PlayRuntimeStatus.ON} or
	 *         {@code PlayRuntimeStatus.OVER}
	 */
	public PlayRuntimeStatus getStatus() {
		return status;
	}

	/**
	 * Gives the player that will play next. By calling this method we can know that
	 * who's turn to play.
	 *
	 * @return instance of {@link Player}
	 */
	public Player getNextPlayer() {
		return players[turn];
	}

	/**
	 * @return List of {@link Player}s
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * Gives the configuration of the running instance of the game.
	 *
	 * @return instance of {@link Params}
	 */
	public Params getParams() {
		return params;
	}

	/**
	 * Gives the game field.
	 *
	 * @return instance of {@link PlayField}
	 */
	public PlayField getPlayField() {
		return field;
	}

	/**
	 * Gives the winner of the game if there is a winner or {@code Optional.empty()}
	 *
	 * @return {@code Optional<Player>}
	 */
	public Optional<Player> getWinner() {
		return Optional.ofNullable(winner);
	}
	
	public void setPlayers(Player[] players) {
		this.players = players;
	}

	/**
	 * This is the method that controls the game.<br/>
	 * Calling this method will put the next players' character on the cell location
	 * of the field.<br/>
	 * After putting the character, the score of the player will be calculated and
	 * game status will be updated.
	 *
	 * @param cell the location of the next character
	 * @throws InvalidCellException if the cell is not inside the field or if the
	 *                              located cell is already full.
	 */
	public void performAction(final Cell cell) throws InvalidCellException {
		Player player = players[turn];
		field.put(player.getSymbol(), cell);
		turn++;

		if (turn == players.length)
			turn = 0;

		if (field.calculateHorizontalScore(cell) >= winScore || field.calculateVerticalScore(cell) >= winScore
				|| field.calculateDiagonal1Score(cell) >= winScore || field.calculateDiagonal2Score(cell) >= winScore) {
			status = PlayRuntimeStatus.OVER;
			winner = player;
		} else if (field.getFreeRoomCount() == 0) {
			status = PlayRuntimeStatus.OVER;
		}
	}
}
