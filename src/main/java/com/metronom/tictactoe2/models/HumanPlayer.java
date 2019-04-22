package com.metronom.tictactoe2.models;

import java.util.Optional;

import com.metronom.tictactoe2.console.ConsoleHandler;
import com.metronom.tictactoe2.engine.PlayEngine;

public class HumanPlayer extends Player {
	
	HumanPlayer(String name, char symbol, boolean aiSupport, ConsoleHandler handler, PlayEngine engine) {
		super(name, symbol, aiSupport, handler, engine);
	}

	@Override
	public Optional<Cell> getNextMove(final PlayField field) {
        return Optional.empty();
	}


}
