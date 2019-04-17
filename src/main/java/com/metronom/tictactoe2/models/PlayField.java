package com.metronom.tictactoe2.models;

import com.metronom.tictactoe2.exceptions.InvalidCellException;
import com.metronom.tictactoe2.ui.ConsoleMessage;

import java.util.Optional;

public class PlayField {
    private static PlayField instance = new PlayField();

    private Character[][] table;
    private int freeRoomCount;
    private int playFieldSize;

    private PlayField() {
    }

    public static PlayField getInstance() {
        return instance;
    }

    /**
     * Initializes the board by setting the {@code playFieldSize} and instantiating the board {@code table} and setting
     * the {@code freeRoomCount}.
     *
     * @param playFieldSize length of the board
     */
    public void init(int playFieldSize) {
        this.playFieldSize = playFieldSize;
        this.table = new Character[playFieldSize][playFieldSize];
        this.freeRoomCount = playFieldSize * playFieldSize;
    }

    /**
     * Puts a character in the {@code cell} location of the board.
     *
     * @param value      the character to put on board
     * @param cell location to put the character
     * @throws InvalidCellException if the {@code cell} is not in the boards length or the cell
     *                                    is already full.
     */
    public void put(final Character value, final Cell cell) throws InvalidCellException {
        if (!isInRange(cell.getRow(), cell.getColumn()))
            throw new InvalidCellException(String.format(ConsoleMessage.INVALID_POINT.getMessageText(), playFieldSize, playFieldSize));

        if (table[cell.getRow()][cell.getColumn()] != null)
            throw new InvalidCellException(ConsoleMessage.CELL_ALREADY_FULL.getMessageText());

        table[cell.getRow()][cell.getColumn()] = value;
        freeRoomCount--;
    }

    /**
     * Gives the free room count in the board.
     *
     * @return {@code freeRoomCount}
     */
    public int getFreeRoomCount() {
        return freeRoomCount;
    }

    /**
     * Gives the board length.
     *
     * @return {@code playFieldSize}
     */
    public int getPlayFieldLength() {
        return playFieldSize;
    }

    /**
     * Calculates the horizontal score of the given cell. Assume that we have a {@code x}
     * at the location {@code {0, 0}} and we have another {@code x} at the location {@code {0, 1}}.
     * In this case, this method will return {@code 2} for both cells.
     *
     * @param cell the location of the board that we want to calculate it's horizontal score.
     * @return score of the given cell.
     */
    public int calculateHorizontalScore(final Cell cell) {
        return calculateScore(cell, 0, -1, 0, 1);
    }

    /**
     * Calculates the vertical score of the given cell. Assume that we have a {@code x}
     * at the location {@code {0, 0}} and we have another {@code x} at the location {@code {1, 0}}.
     * In this case, this method will return {@code 2} for both cells.
     *
     * @param cell the location of the board that we want to calculate it's vertical score.
     * @return score of the given cell.
     */
    public int calculateVerticalScore(final Cell cell) {
        return calculateScore(cell, -1, 0, 1, 0);
    }

    /**
     * Calculates the diagonal1 score of the given cell. Assume that we have a {@code x}
     * at the location {@code {0, 0}} and we have another {@code x} at the location {@code {1, 1}}.
     * In this case, this method will return {@code 2} for both cells.
     *
     * @param cell the location of the board that we want to calculate it's diagonal1 score.
     * @return score of the given cell.
     */
    public int calculateDiagonal1Score(final Cell cell) {
        return calculateScore(cell, -1, -1, 1, 1);
    }

    /**
     * Calculates the diagonal2 score of the given cell. Assume that we have a {@code x}
     * at the location {@code {0, 1}} and we have another {@code x} at the location {@code {1, 0}}.
     * In this case, this method will return {@code 2} for both cells.
     *
     * @param cell the location of the board that we want to calculate it's diagonal2 score.
     * @return score of the given cell.
     */
    public int calculateDiagonal2Score(final Cell cell) {
        return calculateScore(cell, -1, 1, 1, -1);
    }

    /**
     * Gives the value of the given cell as an {@code Optional<Character>}.
     * This method will return {@code Optional.empty} if the location is empty or
     * if the given cell is not in the range of the board.
     *
     * @param row    row number of the board
     * @param column column number of the board
     * @return {@code Optional<Character>}
     */
    public Optional<Character> getCell(final int row, final int column) {
        if (isInRange(row, column))
            return Optional.ofNullable(table[row][column]);

        return Optional.empty();
    }

    /**
     * Indicates that, the given cell is inside the board or not.
     *
     * @param row    row number
     * @param column column number
     * @return {@code true} if the cell is inside the board and otherwise {@code false}
     */
    private boolean isInRange(int row, int column) {
        return row >= 0 && column >= 0 && row < playFieldSize && column < playFieldSize;
    }

    /**
     * This is the main method for calculating the score of a cell in any direction.
     * We can specify the direction using the input parameters.
     * <br/><br/>
     * We have 4 {@code int} parameters that first two parameters specify that when we want to find the start of
     * the line, in which direction we have to move.
     * The next two parameters specify that when we want to find
     * the end of the line, in which direction we have to move.
     *
     * @param cell     the base cell which we want to calculate the score based on that
     * @param startRowInc    the amount of row increment when we want to move toward the start of the line
     * @param startColumnInc the amount of column increment when we want to move toward the start of the line
     * @param endRowInc      the amount of row increment when we want to move toward the end of the line
     * @param endColumnInc   the amount of column increment when we want to move toward the end of the line
     * @return a number equals or greater than 0 that indicates the score of the given cell
     * using the given direction.
     */
    private int calculateScore(final Cell cell, final int startRowInc, final int startColumnInc, final int endRowInc, final int endColumnInc) {
        int startRow = cell.getRow();
        int startColumn = cell.getColumn();
        int endRow = cell.getRow();
        int endColumn = cell.getColumn();

        final Optional<Character> cellValue = getCell(cell.getRow(), cell.getColumn());

        if (cellValue.isPresent()) {
            // Finding the start of the line
            while (isInRange(startRow, startColumn)) {
                if (cellValue.equals(getCell(startRow + startRowInc, startColumn + startColumnInc))) {
                    startRow += startRowInc;
                    startColumn += startColumnInc;
                } else break;
            }

            // Finding the end of the line
            while (isInRange(endRow, endColumn)) {
                if (cellValue.equals(getCell(endRow + endRowInc, endColumn + endColumnInc))) {
                    endRow += endRowInc;
                    endColumn += endColumnInc;
                } else break;
            }

            return Math.max(endRow - startRow + 1, endColumn - startColumn + 1);
        }

        return 0;
    }
}

