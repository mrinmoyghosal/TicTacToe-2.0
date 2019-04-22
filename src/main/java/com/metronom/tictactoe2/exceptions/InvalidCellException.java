package com.metronom.tictactoe2.exceptions;

public class InvalidCellException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidCellException(String message) {
		super(message);
	}
}