package com.metronom.tictactoe2.models;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.metronom.tictactoe2.ui.ConsoleHandler;


@RunWith(PowerMockRunner.class)
@PrepareForTest(PlayField.class)
public class TestAIPlayer {
	
	private static AIPlayer player;
	
	@Mock
	private static PlayField field;
	
	@BeforeClass
	public static void initPlayer() {
		player = new AIPlayer("X", 'Y', true, ConsoleHandler.getInstance());
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
		assertEquals(true, player.isAiSupport());
	}
	
}

