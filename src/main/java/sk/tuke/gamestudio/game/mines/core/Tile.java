package sk.tuke.gamestudio.game.mines.core;

public abstract class Tile {
    private TileState state = TileState.CLOSED;

    public TileState getState() {
        return state;
    }

    void setState(TileState state) {
        this.state = state;
    }

    public boolean isNotOpen(){
        return this.state != TileState.OPEN;
    }
}
