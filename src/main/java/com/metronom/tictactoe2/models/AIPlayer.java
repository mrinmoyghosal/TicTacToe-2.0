package com.metronom.tictactoe2.models;

import java.util.Optional;

import com.metronom.tictactoe2.ui.ConsoleHandler;

public class AIPlayer extends Player {
	
	AIPlayer(String name, char symbol, boolean aiSupport, ConsoleHandler handler) {
		super(name, symbol, aiSupport, handler);
	}

	@Override
	public Optional<Cell> getNextMove(final PlayField field) {
	        
        int length = field.getPlayFieldLength();

        for (int row = 0; row < length; row++) {
            for (int column = 0; column < length; column++) {
                if (!field.getCell(row, column).isPresent())
                    return Optional.of(new Cell(row, column));
            }
        }
   
        return Optional.empty();
	}


}