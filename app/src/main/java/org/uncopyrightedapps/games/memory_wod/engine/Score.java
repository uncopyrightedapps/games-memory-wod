package org.uncopyrightedapps.games.memory_wod.engine;

public class Score implements Comparable {
    private String playerName;
    private int score;

    public Score() {
    }

    public Score(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        return score - ((Score) o).getScore();
    }
}
