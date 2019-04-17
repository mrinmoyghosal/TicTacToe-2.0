package com.metronom.tictactoe2.models;

import java.util.Optional;

import com.metronom.tictactoe2.ui.ConsoleHandler;

public abstract class Player implements PlayerInterface {

	private String name;
    private char symbol;
    private boolean aiSupport;
    private ConsoleHandler handler;

    Player(String name, char symbol, boolean aiSupport, ConsoleHandler handler) {
        this.name = name;
        this.symbol = symbol;
        this.aiSupport = aiSupport;
        this.handler = handler;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

	public boolean isAiSupport() {
		return aiSupport;
	}
	
	
}
