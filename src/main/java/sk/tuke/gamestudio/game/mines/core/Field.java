package sk.tuke.gamestudio.game.mines.core;

import java.util.Random;

public class Field {
    private final int rowCount;

    private final int columnCount;

    private final int mineCount;

    private int openCount;

    private FieldState state = FieldState.PLAYING;

    private final Tile[][] tiles;

    private int score =0;

    private long startTimeInMs = 0;

    public Field(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
        tiles = new Tile[rowCount][columnCount];
        generate();
        startTimeInMs = System.currentTimeMillis();
    }

    private void generate() {
        generateMines();
        fillWithClues();
    }

    private void generateMines() {
        var random = new Random();
        var mineStored = 0;
        while (mineStored < mineCount) {
            var row = random.nextInt(rowCount);
            var column = random.nextInt(columnCount);
            if (tiles[row][column] == null) {
                tiles[row][column] = new Mine();
                mineStored++;
            }
        }
    }

    private void fillWithClues() {
        for (var row = 0; row < rowCount; row++) {
            for (var column = 0; column < columnCount; column++) {
                if (tiles[row][column] == null) {
                    tiles[row][column] = new Clue(countAdjacentMines(row, column));
                }
            }
        }
    }

    private int countAdjacentMines(int row, int column) {
        var count = 0;
        if (row > 0) {
            if (column > 0 && tiles[row - 1][column - 1] instanceof Mine)
                count++;
            if (tiles[row - 1][column] instanceof Mine)
                count++;
            if (column + 1 < columnCount && tiles[row - 1][column + 1] instanceof Mine)
                count++;
        }

        if (column > 0 && tiles[row][column - 1] instanceof Mine)
            count++;
        if (column + 1 < columnCount && tiles[row][column + 1] instanceof Mine)
            count++;

        if (row + 1 < rowCount) {
            if (column > 0 && tiles[row + 1][column - 1] instanceof Mine)
                count++;
            if (tiles[row + 1][column] instanceof Mine)
                count++;
            if (column + 1 < columnCount && tiles[row + 1][column + 1] instanceof Mine)
                count++;
        }

        return count;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getMineCount() {
        return mineCount;
    }

    public FieldState getState() {
        return state;
    }

    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }


    /**
     * Opens a tile. If the game is not played, also computes the score
     * @param row
     * @param column
     */
    public void openTile(int row, int column) {
        var tile = tiles[row][column];
        if (tile.getState() == TileState.CLOSED) {
            tile.setState(TileState.OPEN);
            openCount++;

            if (tile instanceof Mine) {
                state = FieldState.FAILED;
                return;
            }

            if (((Clue) tile).getValue() == 0)
                openAdjacentTiles(row, column);

            if (rowCount * columnCount - mineCount == openCount)
                state = FieldState.SOLVED;
        }

        if(state!=FieldState.PLAYING){
            score=computeScore();
        }
    }

    public void markTile(int row, int column) {
        var tile = tiles[row][column];
        if (tile.getState() == TileState.CLOSED)
            tile.setState(TileState.MARKED);
        else if (tile.getState() == TileState.MARKED)
            tile.setState(TileState.CLOSED);
    }

    private void openAdjacentTiles(int row, int column) {
        if (row > 0) {
            if (column > 0)
                openTile(row - 1, column - 1);
            openTile(row - 1, column);
            if (column + 1 < columnCount)
                openTile(row - 1, column + 1);
        }

        if (column > 0)
            openTile(row, column - 1);
        if (column + 1 < columnCount)
            openTile(row, column + 1);

        if (row + 1 < rowCount) {
            if (column > 0)
                openTile(row + 1, column - 1);
            openTile(row + 1, column);
            if (column + 1 < columnCount)
                openTile(row + 1, column + 1);
        }
    }

    private int computeScore(){

        if(this.state==FieldState.SOLVED){

            final long rawScore =
                    rowCount*columnCount*10 -
                    (System.currentTimeMillis()-startTimeInMs)/1000;
            if(rawScore<=0){
                return 0;
            }else{
                return (int) rawScore;
            }
        }else{
            return 0;
        }
    }

    public int getScore(){
        return score;
    }
}
