package com.metronom.tictactoe2.models;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.metronom.tictactoe2.models.Cell;

public class TestCell {
	
	private static Cell cell;
	
	@BeforeClass
	public static void initCell() {
		cell = new Cell(1, 2);
	}
	
	@Test
	public void testGetRow() {
		assertEquals(1, cell.getRow());
	}
	
	@Test
	public void testGetColumn() {
		assertEquals(2, cell.getColumn());
	}

}
