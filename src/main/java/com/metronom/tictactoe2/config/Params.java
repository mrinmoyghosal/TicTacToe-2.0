package com.metronom.tictactoe2.config;

import java.util.Properties;


public class Params {
	
	private int playFieldSize;
	private char playerOneSymbol;
	private char playerTwoSymbol;
	private char aiPlayerSymbol;
	
	Params(int playFieldSize, char playerOneSymbol, char playerTwoSymbol, char aiPlayerSymbol) {
		this.playFieldSize = playFieldSize;
		this.playerOneSymbol = playerOneSymbol;
		this.playerTwoSymbol = playerTwoSymbol;
		this.aiPlayerSymbol = aiPlayerSymbol;
	}
	
	
	public int getPlayFieldSize() {
		return this.playFieldSize;
	}
	
	public char getPlayerOneSymbol() {
		return this.playerOneSymbol;
	}
	
	public char getPlayerTwoSymbol() {
		return this.playerTwoSymbol;
	}
	
	public char getAIPlayerSymbol() {
		return this.aiPlayerSymbol;
	}
	
	
	
	

}
