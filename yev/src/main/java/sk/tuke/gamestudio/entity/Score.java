package sk.tuke.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "Score.getTopScores",
        query = "SELECT s FROM Score s WHERE s.game=:game ORDER BY s.points DESC")
@NamedQuery(name = "Score.resetScores",
        query = "DELETE FROM Score")


public class Score implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    private String game;
    private String player;
    private int points;
    private Date playedAt;

    public Score() {

    }

    public Score(String game, String player, int points, Date playedAt) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.playedAt = playedAt;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(Date playerAt) {
        this.playedAt = playerAt;
    }


    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", playerAt=" + playedAt +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
