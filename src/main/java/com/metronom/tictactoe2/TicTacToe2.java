package com.metronom.tictactoe2;

import com.metronom.tictactoe2.config.Params;
import com.metronom.tictactoe2.config.ParamsBuilder;
import com.metronom.tictactoe2.console.ConsoleHandler;
import com.metronom.tictactoe2.engine.PlayEngine;

import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicTacToe2 {
	private static Logger logger = Logger.getLogger(TicTacToe2.class.getName());

	public TicTacToe2() {

	}

	/**
	 * Entrance point of the program. Initializes everything and starts the game.
	 *
	 * @param args program input arguments
	 */
	public static void main(String[] args) {

		try {

			ConsoleHandler handler = ConsoleHandler.getInstance();
			ParamsBuilder paramsBuilder = new ParamsBuilder();

			handler.initialize(new InputStreamReader(System.in), System.out, System.err);

			Params config = paramsBuilder.withConsoleHandler(handler).readPlayFieldSize().readPlayerOneSymbol()
					.readPlayerTwoSymbol().readAIPlayerSymbol().build();

			PlayEngine playEngine = new PlayEngine(config);
			handler.setPlayEngine(playEngine);
			handler.startGame();

		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Fatal error", ex);
		}
	}

}