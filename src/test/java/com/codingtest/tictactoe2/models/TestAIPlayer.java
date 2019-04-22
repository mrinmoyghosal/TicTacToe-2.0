package com.codingtest.tictactoe2.models;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.codingtest.tictactoe2.console.ConsoleHandler;
import com.codingtest.tictactoe2.engine.PlayEngine;
import com.codingtest.tictactoe2.models.AIPlayer;
import com.codingtest.tictactoe2.models.Cell;
import com.codingtest.tictactoe2.models.PlayField;


@RunWith(PowerMockRunner.class)
@PrepareForTest(PlayField.class)
public class TestAIPlayer {
	
	private static AIPlayer player;
	
	@Mock
	private PlayField field;
	@Mock
	private PlayEngine engine;
	
	@Before
	public void initPlayer() {	
		player = new AIPlayer("X", 'Y', true, ConsoleHandler.getInstance(), engine);
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
	
	@Test
	public void testGetNextMoveFindsPosition() {
		PowerMockito.mockStatic(PlayField.class);	
		when(PlayField.getInstance()).thenReturn(field);
		when(field.getPlayFieldLength()).thenReturn(3);
		when(field.getCell(0, 0)).thenReturn(Optional.empty());
		Optional<Cell> cell = player.getNextMove(field);
		assertTrue(cell.isPresent());
	}
	
}

