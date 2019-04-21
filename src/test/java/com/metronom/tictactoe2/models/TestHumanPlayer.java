package com.metronom.tictactoe2.models;


import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;

import com.metronom.tictactoe2.console.ConsoleHandler;
import com.metronom.tictactoe2.engine.PlayEngine;

public class TestHumanPlayer {
	
	private static HumanPlayer player;
	private static PlayEngine engine;
	
	@BeforeClass
	public static void initPlayer() {
		player = new HumanPlayer("X", 'Y', false, ConsoleHandler.getInstance(), engine);
	}
	
	@Test
	public void testGetName() {
		assertEquals("X", player.getName());
	}
	
	@Test
	public void testGetSymbol() {
		assertEquals('Y', player.getSymbol());
	}
	
	@Test
	public void testIsAiSupport() {
		assertEquals(false, player.isAiSupport());
	}
	
	@Test
	public void testGetNextMove() {
		assertEquals(Optional.empty(), player.getNextMove(PlayField.getInstance()));
	}
	
}
