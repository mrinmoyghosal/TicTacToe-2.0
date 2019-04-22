package com.metronom.tictactoe2.config;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.metronom.tictactoe2.config.PlayerName;
import com.metronom.tictactoe2.console.ConsoleHandler;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConsoleHandler.class)
public class TestParamsBuilder {
	
    
    @Mock
    private ConsoleHandler handler;
    
    private ParamsBuilder builder; 
    
    @Before
    public void initParamsBuilder() {
    	PowerMockito.mockStatic(ConsoleHandler.class);
    	when(ConsoleHandler.getInstance()).thenReturn(handler);
    	builder = new ParamsBuilder().withConsoleHandler(handler);
    	
    }
 
    @Test
    public void testReadPlayerOneSymbol() {
    	
    	when(handler.readPlayerSymbol(PlayerName.PLAYER_1.getName())).thenReturn('X');
    	Params params = builder
    			.readPlayerOneSymbol()
    			.build();  	
    	assertEquals('X', params.getPlayerOneSymbol());
    	
    }
    
    @Test
    public void testReadPlayerTwoSymbol() {
    	
    	when(handler.readPlayerSymbol(PlayerName.PLAYER_2.getName())).thenReturn('Y');
    	Params params = builder
    			.readPlayerTwoSymbol()
    			.build();
    	assertEquals('Y', params.getPlayerTwoSymbol());
    	
    }
    
    @Test
    public void testReadAIPlayerSymbol() {
    	
    	when(handler.readPlayerSymbol(PlayerName.AIPlayer.getName())).thenReturn('Z');
    	Params params = builder.readAIPlayerSymbol().build();
    	assertEquals('Z', params.getAIPlayerSymbol());
    	
    }
    
    @Test
    public void testReadPlayFieldSize() {
    	
    	when(handler.readPlayFieldSize()).thenReturn(10);
    	Params params = builder.readPlayFieldSize().build();
    	assertEquals(10, params.getPlayFieldSize());
    	
    }
    

}

