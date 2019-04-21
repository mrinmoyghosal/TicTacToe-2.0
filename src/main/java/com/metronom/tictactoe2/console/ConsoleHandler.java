package com.metronom.tictactoe2.console;

import com.metronom.tictactoe2.config.Params;
import com.metronom.tictactoe2.console.ConsoleMessage;
import com.metronom.tictactoe2.engine.PlayEngine;
import com.metronom.tictactoe2.models.PlayRuntimeStatus;
import com.metronom.tictactoe2.models.Cell;
import com.metronom.tictactoe2.models.PlayField;
import com.metronom.tictactoe2.models.Player;
import com.metronom.tictactoe2.exceptions.InvalidCellException;

import java.io.PrintStream;
import java.io.Reader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ConsoleHandler {
	
	// Singleton Instance
    private static ConsoleHandler instance = new ConsoleHandler();
    
    private final int minPlayFieldSize = 3;
    private final int maxPlayFieldSize = 10;

    private Scanner scanner;
    private PrintStream messagesOutput;
    private PrintStream errorsOutput;
    private Set<Character> symbols = new HashSet<>();
	private PlayEngine playEngine;
	
    ConsoleHandler() {
    }

    // Get ConsoleReader singleton instance
    public static ConsoleHandler getInstance() {
        return instance;
    }
    
    public void initialize(Reader usersInput, PrintStream messagesOutput, PrintStream errorsOutput) {
    	this.scanner = new Scanner(usersInput);
        this.messagesOutput = messagesOutput;
        this.errorsOutput = errorsOutput;
    }
    
    public void setPlayEngine(PlayEngine playEngine) {
    	this.playEngine= playEngine;
    }

    /**
     * Starts the main loop for users interaction.
     * <br/><br/>
     * This method starts the playEngine and inside a loop, takes the users input and
     * puts them inside the board using the {@link PlayEngine}'s {@code performAction} method.
     * After that, last move's data will be shown and the playEngine status will be shown.
     * <br/><br/>
     * This loop runs while the playEngine status is {@code PlayRuntimeStatus.ON}
     */
    public void startGame() {
        while (playEngine.getStatus() == PlayRuntimeStatus.ON) {
            Player player = playEngine.getNextPlayer();

            try {
                Cell cell = player.getNextMove(playEngine.getPlayField()).orElseGet(() -> readPlayerMoves(player));
                playEngine.performAction(cell);
                showMessage(String.format(ConsoleMessage.PLAYER_INPUT.getMessageText(), player.getName(), cell.getRow() + 1, cell.getColumn() + 1));
                showStatus();
            } catch (InvalidCellException ex) {
                showError(ex.getMessage());
            }
        }
    }
    
    /**
     * Writes a message to UI's message {@link PrintStream}
     * and waits for flush to avoid interference with other messages.
     *
     * @param message the message to show to the user
     */
    private void showMessage(String message) {
        messagesOutput.println(message);
        messagesOutput.flush();
        waitForFlush();
    }
    
    /**
     * Writes an error to UI's error {@link PrintStream}
     * and waits for flush to avoid interference with other messages.
     *
     * @param error the error to show to the user
     */
    private void showError(String error) {
        errorsOutput.println(error);
        errorsOutput.flush();
        waitForFlush();
    }

    /**
     * Simply sleeps for 10 milliseconds
     */
    private void waitForFlush() {
        try {
            Thread.sleep(10);
        } catch (Exception ignored) { }
    }

    /**
     * Shows the playEngine status and the whole board to user
     */
    private void showStatus() {
        showMessage(String.format(ConsoleMessage.GAME_STATUS.getMessageText(), playEngine.getStatus().getStatus()));
        playEngine.getWinner().ifPresent(winner -> showMessage(String.format(ConsoleMessage.WINNER.getMessageText(), winner.getName(), winner.getSymbol())));
        StringBuilder sb = new StringBuilder();
        
        for (int row = -1; row < playEngine.getPlayField().getPlayFieldLength(); row++) {
            for (int column = -1; column < playEngine.getPlayField().getPlayFieldLength(); column++) {
                if (row == -1 && column > -1)
                    sb.append(" "+ (column + 1)+ " ");
                else if (column == -1 && row > -1)
                    sb.append(" "+ (row + 1)+ " ");
                else sb.append(" "+playEngine.getPlayField().getCell(row, column).orElse(' ')+" ");
                sb.append(" | ");
                
            }
            sb.append("\n");
            
            for (int i = 0; i < playEngine.getPlayField().getPlayFieldLength(); i++) {
            	sb.append("-------");
            }
            sb.append("\n");
        }
        showMessage(sb.toString());
    }
    
    public int readPlayFieldSize() {
    	boolean status = false;
    	int size=0;
    	
    	
    	showMessage(ConsoleMessage.ENTER_PLAYFIELD_SIZE.getMessageText());
    	
    	while (!status) {
    		
    		while (!scanner.hasNextInt()) {
                String input = scanner.next();
                showMessage(String.format(ConsoleMessage.WRONG_VALUE.getMessageText(),input));
            }
    		size = scanner.nextInt();
    		status = validatePlayFieldSize(size);
        	if(!status) showMessage(ConsoleMessage.INVALID_BOARD_LENGTH.getMessageText());
    	}
    	return size;
    }
    
    public char readPlayerSymbol(String playerName) {
    	char symbol = 0;
    	boolean ok = false;
    	showMessage(String.format(ConsoleMessage.ENTER_PLAYER_SYMBOL.getMessageText(), playerName));
    		
		while (!ok) {
	        symbol = scanner.next().charAt(0);
	        if(symbols.contains(Character.toLowerCase(symbol))) {
	        	showMessage(ConsoleMessage.SYMBOL_ALREADY_CHOSEN.getMessageText());
	        	continue;
	        }
	        if (!Character.isLetter(symbol)) {
	        	showMessage(String.format(ConsoleMessage.WRONG_VALUE.getMessageText(), symbol));
	        	continue;
	        }
	        ok = true;
	    }
    	symbols.add(Character.toLowerCase(symbol));
    	return symbol;
	}
    
    private boolean validatePlayFieldSize(int size) {
    	
    	boolean isValid = size <= maxPlayFieldSize && size >= minPlayFieldSize? true: false;
    	return isValid;
    }

    /**
     * Reads user input from command line interface. This method keeps prompting the user while
     * the input value is not valid.
     *
     * @param player the player that should give the next input
     * @return next {@link Coordinate} given by the player
     */
    private Cell readPlayerMoves(Player player) {
        Cell cell = null;
        String response;

        while (cell == null) {
            showMessage(String.format(ConsoleMessage.ENTER_NEXT_POINT.getMessageText(), player.getName(), player.getSymbol()));
            
        	
            String location = scanner.nextLine();
            
            if (location.isEmpty()) location = scanner.nextLine();
            
        	try {
        		int x = Character.getNumericValue(location.charAt(0));
        		char sep = location.charAt(1);
        		int y = Character.getNumericValue(location.charAt(2));
        		
        		
	        	boolean isValidX = x >= 1 && x <= maxPlayFieldSize? true: false;
	        	boolean isValidY = y >= 1 && y <= maxPlayFieldSize? true: false;
	        	boolean isValidSep = sep == ','? true: false;
	        	
	        	if (isValidX && isValidY && isValidSep) {
	        	
		            int row = x - 1;
		            int column = y - 1;
		            cell = new Cell(row, column);
	        	}else {
	        		showMessage(String.format(ConsoleMessage.WRONG_VALUE.getMessageText(), location));
	        	}
	        	
        	}catch(StringIndexOutOfBoundsException e) {
        		showMessage(String.format(ConsoleMessage.WRONG_VALUE.getMessageText(), location));
        	}
        	
            
        }

        return cell;
    }
    
}