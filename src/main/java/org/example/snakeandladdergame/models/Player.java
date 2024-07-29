package org.example.snakeandladdergame.models;

/**
 * Player.java
 *
 * @author Mohamed Subaideen Imran A (mohamedsubaideenimran@nmsworks.co.in)
 * @module com.example.snakeAndLadder.models
 * @created Jul 14, 2024
 */
public class Player
{
    private String playerName;
    private int playerFromPosition;
    private int playerPosition;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }

    public int getPlayerFromPosition() {
        return playerFromPosition;
    }

    public void setPlayerFromPosition(int playerFromPosition) {
        this.playerFromPosition = playerFromPosition;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", playerFromPosition=" + playerFromPosition +
                ", playerPosition=" + playerPosition +
                '}';
    }
}
