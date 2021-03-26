package main.java.sk.tuke.gamestudio.entity;

import java.io.Serializable;
import java.util.Date;

public class Score implements Serializable {
    private String game;

    private String player;

    private int points;

    private Date playedat;

    public Score(String game, String player, int points, Date playedat) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.playedat = playedat;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedAt() {
        return playedat;
    }

    public void setPlayedAt(Date playedat) {
        this.playedat = playedat;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", playedOn=" + playedat +
                '}';
    }

}
