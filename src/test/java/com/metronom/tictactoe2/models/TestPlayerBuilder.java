package com.metronom.tictactoe2.models;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.metronom.tictactoe2.ui.ConsoleHandler;

public class TestPlayerBuilder {
	
	private PlayerBuilder builder;
	private ConsoleHandler handler = ConsoleHandler.getInstance();

	
	@Before
	public void initPlayerBuilder() {
		builder = new PlayerBuilder();
	}
	
	@Test
	public void testWithName() {
		builder = builder.withName("X");
		assertEquals("X", builder.getName());
	}
	
	@Test
	public void testWithSymbol() {
		builder = builder.withSymbol('X');
		assertEquals('X', builder.getSymbol());
	}
	
	@Test
	public void testWithHandler() {
		builder = builder.withConsoleHandler(handler);
		assertEquals(handler, builder.getHandler());
	}
	
	@Test
	public void testWithAISupport() {
		assertEquals(false, builder.isAiSupport());
		builder = builder.withAISupport();
		assertEquals(true, builder.isAiSupport());
	}
	
	@Test
	public void testBuildHumanPlayer() {
		Player humanPlayer = builder.withName("X").withConsoleHandler(handler).withSymbol('X').build();
		assertEquals(HumanPlayer.class, humanPlayer.getClass());
		
	}
	
	@Test
	public void testBuildAIPlayer() {
		Player aiPlayer = builder.withName("X").withConsoleHandler(handler).withSymbol('X').withAISupport().build();
		assertEquals(AIPlayer.class, aiPlayer.getClass());
		
	}
	
}
