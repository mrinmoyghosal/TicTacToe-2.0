package com.codingtest.tictactoe2.console;

public enum ConsoleMessage {

	ENTER_PLAYFIELD_SIZE("** Please enter Playfield size from 3 to 10 **"),

	ENTER_PLAYER_SYMBOL("** Please enter %s Symbol (One character only) **"),

	SYMBOL_ALREADY_CHOSEN("** Symbol already chosen by another player. Please enter a different character **"),

	INPUT_STREAM_IS_NULL("Input Stream is Null"),

	LOAD_ERROR("Loading Error"),

	INVALID_BOARD_LENGTH("Invalid Board Length (Valid range is 3 to 10)"),

	DUPLICATE_SYMBOLS("One of the player already chosen this character"),

	INVALID_POINT("Invalid Coordinate"),

	CELL_ALREADY_FULL("Cell already played"),

	START_BANNER("Welcome to Tic-Tac-Toe 2"),

	CONFIGS("=== GAME CONFIG [Board Size: %s], Player 1 [%s], Player 2 [%s], AIPlayer [%s] ==="),

	ENTER_NEXT_POINT("=== Name - [%s] Symbol - [%s], Enter your move (format - 1,2) ==="),

	WRONG_VALUE("Input value is wrong - %s"),

	PLAYER_INPUT("Player Name - [%s], Position used - [%s,%s]"),

	GAME_STATUS("%s"),

	WINNER("Winner is %s");

	String str = null;

	ConsoleMessage(String str) {
		this.str = str;
	}

	public String getMessageText() {
		return this.str;
	}
}
