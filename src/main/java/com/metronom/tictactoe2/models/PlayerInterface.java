package com.metronom.tictactoe2.models;

import java.util.Optional;

public interface PlayerInterface {
		
	/**
	 * Get name of the player
	 * @return player's name
	 */
	public String getName();
	
	/**
	 * Get player's symbol
	 * @return player's symbol as char
	 */
	public char getSymbol();
	
	/**
	 * Return true if the player is AI else false
	 * @return
	 */
	public boolean isAiSupport();
	
	/**
     * Accepts an instance of {@link PlayField} and calculates next move based on board status.
     * If this is not an automatic player, this method will return an {@code Optional.empty()}
     * 
     * @param board copy of board
     * @return the next move of the AI player or {@code Optional.empty()} for non-ai players.
     */
	public Optional<Cell> getNextMove(final PlayField field);
	


}
