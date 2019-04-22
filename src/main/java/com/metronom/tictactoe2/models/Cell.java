package com.metronom.tictactoe2.models;

public class Cell {

	private int row;
	private int column;

	public Cell(final int row, final int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

}
