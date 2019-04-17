package com.metronom.tictactoe2.models;

import java.util.Optional;
import com.metronom.tictactoe2.ui.ConsoleHandler;

public class HumanPlayer extends Player {
	
	HumanPlayer(String name, char symbol, boolean aiSupport, ConsoleHandler handler) {
		super(name, symbol, aiSupport, handler);
	}

	@Override
	public Optional<Cell> getNextMove(final PlayField field) {
        return Optional.empty();
	}


}
