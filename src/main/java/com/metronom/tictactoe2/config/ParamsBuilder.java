package com.metronom.tictactoe2.config;

import com.metronom.tictactoe2.console.ConsoleHandler;

enum PlayerName{
	PLAYER_1("Player 1"),
	PLAYER_2("Player 2"),
	AIPlayer("AI Player");
	
	public String name;
	
	PlayerName(String name){
		this.name = name;
	}
	
	String getName(){
		return this.name;
	}
}

public class ParamsBuilder {
	
	private ConsoleHandler handler;
	private int playFieldSize;
	private char playerOneSymbol;
	private char playerTwoSymbol;
	private char aiPlayerSymbol;
	
	

	/*
	 * Initialize ParamsBuilder
	 */
	public ParamsBuilder() {
	}
	
	public ParamsBuilder withConsoleHandler(ConsoleHandler handler) {
		this.handler = handler;
		return this;
	}
	
	/*
	 * Read Playfield size from console
	 */
	public ParamsBuilder readPlayFieldSize() {
		int size = handler.readPlayFieldSize();
		this.playFieldSize = size;
		return this;
	}
	/*
	 * Read player one symbol from console
	 */
	public ParamsBuilder readPlayerOneSymbol() {
		char symbol = handler.readPlayerSymbol(PlayerName.PLAYER_1.getName());
		this.playerOneSymbol = symbol;
		return this;
	}
	
	/*
	 * Read player two symbol from console
	 */
	public ParamsBuilder readPlayerTwoSymbol() {
		char symbol = handler.readPlayerSymbol(PlayerName.PLAYER_2.getName());
		this.playerTwoSymbol = symbol;
		return this;
	}
	
	/*
	 * Read AI Player symbol from console
	 */
	public ParamsBuilder readAIPlayerSymbol() {
		char symbol = handler.readPlayerSymbol(PlayerName.AIPlayer.getName());
		this.aiPlayerSymbol = symbol;
		return this;
	}
	
	/*
	 * Initialize and return the @link Params object
	 */
	public Params build() {
		return new Params(playFieldSize, playerOneSymbol, playerTwoSymbol, aiPlayerSymbol);
	}

}
