package com.codingtest.tictactoe2.models;

import com.codingtest.tictactoe2.console.ConsoleHandler;
import com.codingtest.tictactoe2.engine.PlayEngine;

public class PlayerBuilder {

	private String name;
	private char symbol;
	private boolean aiSupport = false;
	private ConsoleHandler handler;
	private PlayEngine engine;

	public PlayerBuilder() {
	}

	/**
	 * Getters
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	public char getSymbol() {
		return symbol;
	}

	public boolean isAiSupport() {
		return aiSupport;
	}

	public ConsoleHandler getHandler() {
		return handler;
	}

	/**
	 * Sets the name for the {@link Player} that will be created by calling the
	 * {@code build} method.
	 *
	 * @param name name of the player
	 * @return the same {@link PlayerBuilder}
	 */
	public PlayerBuilder withName(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * Sets the name for the {@link Player} that will be created by calling the
	 * {@code build} method.
	 *
	 * @param name name of the player
	 * @return the same {@link PlayerBuilder}
	 */
	public PlayerBuilder withEngineContext(final PlayEngine engine) {
		this.engine = engine;
		return this;
	}

	/**
	 * Sets the symbol for the {@link Player} that will be created by calling the
	 * {@code build} method.
	 *
	 * @param symbol symbol of the player
	 * @return the same {@link PlayerBuilder}
	 */
	public PlayerBuilder withSymbol(final char symbol) {
		this.symbol = symbol;
		return this;
	}

	public PlayerBuilder withConsoleHandler(final ConsoleHandler handler) {
		this.handler = handler;
		return this;
	}

	/**
	 * Sets the aiSupport to {@code true} for the {@link Player} that will be
	 * created by calling the {@code build} method.
	 *
	 * @return the same {@link PlayerBuilder}
	 */
	public PlayerBuilder withAISupport() {
		this.aiSupport = true;
		return this;
	}

	/**
	 * Builds a {@link Player} using the builder parameters.
	 *
	 * @return instance of {@link Player}
	 */
	public Player build() {
		if (aiSupport) {
			return new AIPlayer(name, symbol, aiSupport, handler, engine);
		}

		return new HumanPlayer(name, symbol, aiSupport, handler, engine);
	}
}
