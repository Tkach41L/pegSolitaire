package sk.tuke.gamestudio.game.core;

import java.io.Serializable;

public class tileWeb implements Serializable {
    private Tile state = Tile.NONE;


    public Tile getState() {
        return state;
    }

    void setState(Tile state) {
        this.state = state;
    }

}
