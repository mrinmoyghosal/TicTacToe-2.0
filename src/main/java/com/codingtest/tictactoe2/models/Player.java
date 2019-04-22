package com.codingtest.tictactoe2.models;

import java.util.Optional;

import com.codingtest.tictactoe2.console.ConsoleHandler;
import com.codingtest.tictactoe2.engine.PlayEngine;

public abstract class Player implements PlayerInterface {

	private String name;
	private char symbol;
	private boolean aiSupport;
	private ConsoleHandler handler;
	private PlayEngine engine;

	Player(String name, char symbol, boolean aiSupport, ConsoleHandler handler, PlayEngine engine) {
		this.name = name;
		this.symbol = symbol;
		this.aiSupport = aiSupport;
		this.handler = handler;
		this.engine = engine;
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

	public PlayEngine getEngine() {
		return engine;
	}

}
