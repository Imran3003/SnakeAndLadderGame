package org.example.snakeandladdergame.models;

/**
 * SnakeAndLadder.java
 *
 * @author Mohamed Subaideen Imran A (mohamedsubaideenimran@nmsworks.co.in)
 * @module com.example.snakeAndLadder.models
 * @created Jul 14, 2024
 */
public class SnakeAndLadder
{
    private int position;
    private boolean snakeIsPresent;
    private boolean ladderIsPresent;
    private int toPosition;

    public SnakeAndLadder() {
    }

    public SnakeAndLadder(int position, boolean snakeIsPresent, boolean ladderIsPresent, int toPosition) {
        this.position = position;
        this.snakeIsPresent = snakeIsPresent;
        this.ladderIsPresent = ladderIsPresent;
        this.toPosition = toPosition;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSnakeIsPresent() {
        return snakeIsPresent;
    }

    public void setSnakeIsPresent(boolean snakeIsPresent) {
        this.snakeIsPresent = snakeIsPresent;
    }

    public boolean isLadderIsPresent() {
        return ladderIsPresent;
    }

    public void setLadderIsPresent(boolean ladderIsPresent) {
        this.ladderIsPresent = ladderIsPresent;
    }

    public int getToPosition() {
        return toPosition;
    }

    public void setToPosition(int toPosition) {
        this.toPosition = toPosition;
    }

    @Override
    public String toString() {
        return "SnakeAndLadder{" +
                "position=" + position +
                ", snakeIsPresent=" + snakeIsPresent +
                ", ladderIsPresent=" + ladderIsPresent +
                ", toPosition=" + toPosition +
                '}';
    }
}
