package com.codingtest.tictactoe2;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.codingtest.tictactoe2.TicTacToe2;
import com.codingtest.tictactoe2.config.Params;
import com.codingtest.tictactoe2.config.ParamsBuilder;
import com.codingtest.tictactoe2.console.ConsoleHandler;
import com.codingtest.tictactoe2.engine.PlayEngine;
import com.codingtest.tictactoe2.models.AIPlayer;
import com.codingtest.tictactoe2.models.PlayField;
import com.codingtest.tictactoe2.models.PlayRuntimeStatus;
import com.codingtest.tictactoe2.models.Player;
import com.codingtest.tictactoe2.models.PlayerBuilder;

@RunWith(PowerMockRunner.class)
public class TestTicTacToe2 {

	private ConsoleHandler handler = ConsoleHandler.getInstance();
	
	private PlayEngine engine;
	
	Player[] players = new Player[3];
	
	@Test
	public void testTicTacToe2SimpleMoves() {
		
		String inputCommands = String.join("\n"
		         , "3" // Playfield size
		         , "A" // AI Player Symbol
		         , "X" // Player 1 Symbol
		         , "Y" // Player 2 Symbol
		         , "2,2" // AI Player Move
		         ,"2,2" // Invalid Move
		         , "1,1" // Player 1 Move
		         , "1,2" // Player 2 Move
		         , "1,3" // AI Player Move
		         ,"3,3" // Player 1 Move
		         ,"2,3" // Player 2 Move
		         ,"3,1" // AI Player Move
		);
		
		
		players[0] = new PlayerBuilder().withConsoleHandler(handler).withEngineContext(engine).withName("AI").withSymbol('A').build();
		players[1] = new PlayerBuilder().withConsoleHandler(handler).withEngineContext(engine).withName("P1").withSymbol('X').build();
		players[2] = new PlayerBuilder().withConsoleHandler(handler).withEngineContext(engine).withName("P2").withSymbol('Y').build();
		
		Params params = new Params(3,'A','X','Y');
		engine = new PlayEngine(params);
		engine.setPlayers(players);
		
		System.setIn(new ByteArrayInputStream(inputCommands.getBytes()));
		handler.initialize(new InputStreamReader(System.in), System.out, System.err);
		handler.setPlayEngine(engine);
		
		handler.startGame();
		
		assertEquals(engine.getPlayers()[0].getName(), engine.getWinner().get().getName());
	}
	
	@Test
	public void testTicTacToe2WithoutAIPlayer() {
		
		String inputCommands = String.join("\n"
				 , "50" // Invalid Playfield Size
				 , "2"
				 , "a"  // Invalid Playfield value
		         , "3" // Playfield size
		         , "1" // Invalid Player Symbol
		         , "A" // AI Player Symbol
		         , "A" // Invalid symbol (already chosen) 
		         , "X" // Player 1 Symbol
		         , "Y" // Player 2 Symbol
		         , "2+2" // AI Player Move // wrong separator
		         , "2,2" // AI Player Move
		         , "2,2" // Invalid Move
		         , "1,1" // Player 1 Move
		         , "1,2" // Player 2 Move
		         , "1,3" // AI Player Move
		         , "3,3" // Player 1 Move
		         , "2,3" // Player 2 Move
		         , "5,5" //invalid move
		         , "3,1" // AI Player Move
		);
		
		
		players[0] = new PlayerBuilder().withConsoleHandler(handler).withEngineContext(engine).withName("AI").withSymbol('A').build();
		players[1] = new PlayerBuilder().withConsoleHandler(handler).withEngineContext(engine).withName("P1").withSymbol('X').build();
		players[2] = new PlayerBuilder().withConsoleHandler(handler).withEngineContext(engine).withName("P2").withSymbol('Y').build();
		
		
		System.setIn(new ByteArrayInputStream(inputCommands.getBytes()));
		handler.initialize(new InputStreamReader(System.in), System.out, System.err);
		
		ParamsBuilder paramsBuilder = new ParamsBuilder();
		Params config = paramsBuilder.withConsoleHandler(handler).readPlayFieldSize().readAIPlayerSymbol().readPlayerOneSymbol()
				.readPlayerTwoSymbol().build();
		
		engine = new PlayEngine(config);
		
		engine.setPlayers(players);
	
		handler.setPlayEngine(engine);
		
		handler.startGame();
		
		assertEquals(engine.getPlayers()[0].getName(), engine.getWinner().get().getName());
	}
	
	@Test
	public void testTicTacToe2WithAIPlayer() {
		
		String inputCommands = String.join("\n"
				 , "50" // Invalid Playfield Size
				 , "2"
				 , "a"  // Invalid Playfield value
		         , "3" // Playfield size
		         , "M" // AI Player Symbol
		         , "O" // Player 1 Symbol
		         , "P" // Player 2 Symbol
		         , "1,1" // Random Moves
		         , "1,2" // Random Moves
		         , "1,3" // Random Moves
		         , "2,1" // Random Moves
		         , "2,2" // Random Moves
		         , "2,3" // Random Moves
		         , "3,1" // Random Moves
		         , "3,2" // Random Moves
		         , "3,3" // Random Moves
		         , "3,1" // Random Moves
		         , "1,1" // Random Moves
		         , "1,2" // Random Moves
		         , "1,3" // Random Moves
		         , "2,1" // Random Moves
		         , "2,2" // Random Moves
		         , "2,3" // Random Moves
		         , "3,1" // Random Moves
		         , "3,2" // Random Moves
		         , "3,3" // Random Moves
		         , "3,1" // Random Moves
		         , "1,1" // Random Moves
		         , "1,2" // Random Moves
		         , "1,3" // Random Moves
		         , "2,1" // Random Moves
		         , "2,2" // Random Moves
		         , "2,3" // Random Moves
		         , "3,1" // Random Moves
		         , "3,2" // Random Moves
		         , "3,3" // Random Moves
		         , "3,1" // Random Moves
		         , "1,1" // Random Moves
		         , "1,2" // Random Moves
		         , "1,3" // Random Moves
		         , "2,1" // Random Moves
		         , "2,2" // Random Moves
		         , "2,3" // Random Moves
		         , "3,1" // Random Moves
		         , "3,2" // Random Moves
		         , "3,3" // Random Moves
		);
		
	
		System.setIn(new ByteArrayInputStream(inputCommands.getBytes()));
		handler.initialize(new InputStreamReader(System.in), System.out, System.err);
		
		TicTacToe2.main(null);
		
		assertEquals(false, TicTacToe2.isRunning);
	}

}
