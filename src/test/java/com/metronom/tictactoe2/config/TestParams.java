package com.metronom.tictactoe2.config;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestParams {
	
	public static Params param;
	
	@BeforeClass
	public static void initParams() {
		int playFieldSize = 3;
		char playerOneSymbol = 'X';
		char playerTwoSymbol = 'Y';
		char aiPlayerSymbol = 'Z';
		param = new Params(playFieldSize, playerOneSymbol, playerTwoSymbol, aiPlayerSymbol);
	}

	@Test
	public void testGetPlayFieldSize() {
		assertEquals(3, param.getPlayFieldSize());
	}
	
	@Test
	public void testGetPlayerSymbol() {
		assertEquals('X', param.getPlayerOneSymbol());
		assertEquals('Y', param.getPlayerTwoSymbol());
		assertEquals('Z', param.getAIPlayerSymbol());
	}

}
