package com.metronom.tictactoe2.models;

import com.metronom.tictactoe2.exceptions.InvalidCellException;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestPlayField {
    private PlayField field = PlayField.getInstance();

    @Before
    public void setUp() throws Exception {
        field.init(3);
    }

    @Test
    public void init() {
        assertEquals(3, field.getPlayFieldLength());
        assertEquals(9, field.getFreeRoomCount());
    }

    @Test
    public void put() throws InvalidCellException {
        field.put('x', new Cell(0, 0));
        assertEquals(Optional.of('x'), field.getCell(0, 0));
        assertEquals(field.getPlayFieldLength() * field.getPlayFieldLength() - 1, field.getFreeRoomCount());
    }

    @Test(expected = InvalidCellException.class)
    public void putInInvalidCell() throws InvalidCellException {
        field.put('x', new Cell(-1, 0));
    }

    @Test(expected = InvalidCellException.class)
    public void putDuplicate() throws InvalidCellException {
        field.put('x', new Cell(0, 0));
        field.put('o', new Cell(0, 0));
    }

    @Test
    public void getFreeRoomCount() {
        assertEquals(field.getPlayFieldLength() * field.getPlayFieldLength(), field.getFreeRoomCount());
    }

    @Test
    public void getPlayFieldLength() {
        assertEquals(3, field.getPlayFieldLength());
    }

    @Test
    public void calculateHorizontalScore() throws InvalidCellException {
        assertEquals(0, field.calculateHorizontalScore(new Cell(0, 0)));
        field.put('x', new Cell(0, 0));
        assertEquals(1, field.calculateHorizontalScore(new Cell(0, 0)));
        field.put('x', new Cell(0, 1));
        assertEquals(2, field.calculateHorizontalScore(new Cell(0, 0)));
        field.put('x', new Cell(0, 2));
        assertEquals(3, field.calculateHorizontalScore(new Cell(0, 0)));
    }

    @Test
    public void calculateVerticalScore() throws InvalidCellException {
        assertEquals(0, field.calculateVerticalScore(new Cell(0, 0)));
        field.put('x', new Cell(0, 0));
        assertEquals(1, field.calculateVerticalScore(new Cell(0, 0)));
        field.put('x', new Cell(1, 0));
        assertEquals(2, field.calculateVerticalScore(new Cell(0, 0)));
        field.put('x', new Cell(2, 0));
        assertEquals(3, field.calculateVerticalScore(new Cell(0, 0)));
    }

    @Test
    public void calculateDiagonal1Score() throws InvalidCellException {
        assertEquals(0, field.calculateDiagonal1Score(new Cell(0, 0)));
        field.put('x', new Cell(0, 0));
        assertEquals(1, field.calculateDiagonal1Score(new Cell(0, 0)));
        field.put('x', new Cell(1, 1));
        assertEquals(2, field.calculateDiagonal1Score(new Cell(0, 0)));
        field.put('x', new Cell(2, 2));
        assertEquals(3, field.calculateDiagonal1Score(new Cell(0, 0)));
    }

    @Test
    public void calculateDiagonal2Score() throws InvalidCellException {
        assertEquals(0, field.calculateDiagonal2Score(new Cell(0, 2)));
        field.put('x', new Cell(0, 2));
        assertEquals(1, field.calculateDiagonal2Score(new Cell(0, 2)));
        field.put('x', new Cell(1, 1));
        assertEquals(2, field.calculateDiagonal2Score(new Cell(0, 2)));
        field.put('x', new Cell(2, 0));
        assertEquals(3, field.calculateDiagonal2Score(new Cell(0, 2)));
    }

    @Test
    public void getCell() throws InvalidCellException {
        assertFalse(field.getCell(0, 0).isPresent());
        field.put('x', new Cell(0, 0));
        assertTrue(field.getCell(0, 0).isPresent());
        assertEquals(Optional.of('x'), field.getCell(0, 0));
    }
    
    @Test
    public void testEvaluate() {
    	Character[][] table = field.getTable();
    	table[0][0] = 'X';
    	
    	int score = field.evaluate(table, 'X');
    	assertEquals(-4, score);
    			
    	
    }
}