package com.metronom.tictactoe2.engine;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.metronom.tictactoe2.models.PlayRuntimeStatus;
import com.metronom.tictactoe2.models.Cell;
import com.metronom.tictactoe2.models.PlayField;
import com.metronom.tictactoe2.models.Player;
import com.metronom.tictactoe2.config.Params;
import com.metronom.tictactoe2.console.ConsoleHandler;
import com.metronom.tictactoe2.exceptions.InvalidCellException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.StringReader;
import java.util.Optional;

public class TestPlayEngine {
    
	private PlayEngine engine;
	Params params = Mockito.mock(Params.class);
	
    @Before
    public void setUp() throws Exception {
    	
        when(params.getPlayFieldSize()).thenReturn(3);
        when(params.getAIPlayerSymbol()).thenReturn('A');
        when(params.getPlayerOneSymbol()).thenReturn('X');
        when(params.getPlayerTwoSymbol()).thenReturn('Y');
        
        engine = new PlayEngine(params);
    }

    @Test
    public void getStatus() {
        assertEquals(PlayRuntimeStatus.ON, engine.getStatus());
    }

    @Test
    public void getNextPlayer() throws InvalidCellException {
        Player player1 = engine.getNextPlayer();
        assertNotNull(player1);
        assertEquals(player1, engine.getNextPlayer());
        assertEquals('A', player1.getSymbol());

        engine.performAction(new Cell(0, 0)); //x
        Player player2 = engine.getNextPlayer();
        assertNotEquals(player1, player2);
        assertEquals('X', player2.getSymbol());

        engine.performAction(new Cell(0, 1)); //y
        Player player3 = engine.getNextPlayer();
        assertNotEquals(player1, player3);
        assertEquals('Y', player3.getSymbol());

        engine.performAction(new Cell(0, 2)); //c
        Player player4 = engine.getNextPlayer();
        assertEquals(player1, player4);
    }

    @Test
    public void getConfig() {
        assertEquals(params, engine.getParams());
    }

    @Test
    public void getPlayField() {
        assertNotNull(engine.getPlayField());
    }

    @Test
    public void getWinner() {
        assertFalse(engine.getWinner().isPresent());
    }

    @Test(expected = InvalidCellException.class)
    public void performActionWrongCoordinate() throws InvalidCellException {
        engine.performAction(new Cell(-1, 20)); //x
    }

    @Test(expected = InvalidCellException.class)
    public void performActionDuplicatePlay() throws InvalidCellException {
        engine.performAction(new Cell(0, 0)); //x
        engine.performAction(new Cell(0, 0)); //o
    }

    @Test
    public void performActionFullGameWithWinner() throws InvalidCellException {
        Player player1 = engine.getNextPlayer();
        assertEquals('A', player1.getSymbol());
        engine.performAction(new Cell(0, 0)); //a
        assertEquals(PlayRuntimeStatus.ON, engine.getStatus());
        assertFalse(engine.getWinner().isPresent());

        Player player2 = engine.getNextPlayer();
        assertEquals('X', player2.getSymbol());
        engine.performAction(new Cell(1, 0)); //x
        assertEquals(PlayRuntimeStatus.ON, engine.getStatus());
        assertFalse(engine.getWinner().isPresent());

        Player player3 = engine.getNextPlayer();
        assertEquals('Y', player3.getSymbol());
        engine.performAction(new Cell(2, 0)); //y
        assertEquals(PlayRuntimeStatus.ON, engine.getStatus());
        assertFalse(engine.getWinner().isPresent());

        assertNotEquals(player1, player2);
        assertNotEquals(player1, player3);
        assertNotEquals(player2, player3);

        engine.performAction(new Cell(0, 1)); //A
        engine.performAction(new Cell(1, 1)); //X
        engine.performAction(new Cell(2, 1)); //Y

        engine.performAction(new Cell(0, 2)); //A

        assertEquals(PlayRuntimeStatus.OVER, engine.getStatus());
        assertEquals(Optional.of(player1), engine.getWinner());
    }

    @Test
    public void performActionFullGameWithoutWinnerHorizontal() throws InvalidCellException {
        engine.performAction(new Cell(0, 0)); //A
        engine.performAction(new Cell(1, 0)); //X
        engine.performAction(new Cell(2, 0)); //Y

        engine.performAction(new Cell(0, 1)); //A
        engine.performAction(new Cell(1, 1)); //X
        engine.performAction(new Cell(2, 1)); //Y

        engine.performAction(new Cell(1, 2)); //A
        engine.performAction(new Cell(2, 2)); //X
        engine.performAction(new Cell(0, 2)); //Y

        assertEquals(PlayRuntimeStatus.OVER, engine.getStatus());
        assertFalse(engine.getWinner().isPresent());
    }
    
    
    @Test
    public void performActionFullGameWithoutWinnerVertical() throws InvalidCellException {
        engine.performAction(new Cell(0, 0)); //A
        engine.performAction(new Cell(0, 1)); //X
        engine.performAction(new Cell(0, 2)); //Y

        engine.performAction(new Cell(1, 0)); //A
        engine.performAction(new Cell(1, 1)); //X
        engine.performAction(new Cell(1, 2)); //Y

        engine.performAction(new Cell(2, 0)); //A
        engine.performAction(new Cell(2, 1)); //X
        engine.performAction(new Cell(2, 2)); //Y

        assertEquals(PlayRuntimeStatus.OVER, engine.getStatus());
        assertTrue(engine.getWinner().isPresent());
    }
    
    @Test
    public void performActionFullGameWithoutWinnerDiagonal() throws InvalidCellException {
        engine.performAction(new Cell(0, 0)); //A
        engine.performAction(new Cell(0, 1)); //X
        engine.performAction(new Cell(0, 2)); //Y

        engine.performAction(new Cell(1, 1)); //A
        engine.performAction(new Cell(1, 0)); //X
        engine.performAction(new Cell(1, 2)); //Y

        engine.performAction(new Cell(2, 2)); //A
        engine.performAction(new Cell(2, 0)); //X
        engine.performAction(new Cell(2, 1)); //Y

        assertEquals(PlayRuntimeStatus.OVER, engine.getStatus());
        assertTrue(engine.getWinner().isPresent());
    }
    
    @Test
    public void testGetPlayers() {
        Player[] players = engine.getPlayers();
        assertEquals(3, players.length);
        
    }

}
