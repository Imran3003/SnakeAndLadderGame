package org.example.snakeandladdergame.utils;

import org.example.snakeandladdergame.models.SnakeAndLadder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Utils
{
    public static int generateRandomIndex(int start, int end)
    {
        Random rand = new Random();
        return start + rand.nextInt(end - start + 1);
    }

    public static List<StringBuilder> loadSnakeAndLadderBoard(SnakeAndLadder[][] board, int numberOfPlayers) {
        List<StringBuilder> snakeAndLadderBoardList = new ArrayList<>();

        StringBuilder snakeAndLadderHtml = new StringBuilder();
        StringBuilder ladderAndSnakeHtml = new StringBuilder();

        ladderAndSnakeHtml.append("const ladders = [");

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                SnakeAndLadder snakeAndLadder = board[i][j];

                String squareColour = getSqureColour(j, i);
                String backgroundColor = "background-color: " + squareColour + ";";

                String squareHtml;

                if (i == 9 && j == 0)
                    squareHtml = playerCoin(backgroundColor, numberOfPlayers);
                else
                    squareHtml = String.format("<div id=\"%s\" class=\"snakeAndLadder\" style=\"%s\">%s</div>", snakeAndLadder.getPosition(), backgroundColor, snakeAndLadder.getPosition());

                snakeAndLadderHtml.append(squareHtml);

                if (snakeAndLadder.isLadderIsPresent()) {
                    ladderAndSnakeHtml.append(String.format(
                            "{start: %d, end: %d, is:\"ladder\"},",
                            snakeAndLadder.getPosition(),
                            snakeAndLadder.getToPosition()
                    ));
                }
                if(snakeAndLadder.isSnakeIsPresent()) {
                    ladderAndSnakeHtml.append(String.format(
                            "{start: %d, end: %d, is:\"snake\"},",
                            snakeAndLadder.getPosition(),
                            snakeAndLadder.getToPosition()
                    ));
                }
            }
        }

        // Remove trailing comma and close the array and script tag
        if (ladderAndSnakeHtml.charAt(ladderAndSnakeHtml.length() - 1) == ',') {
            ladderAndSnakeHtml.setCharAt(ladderAndSnakeHtml.length() - 1, ']');
        } else {
            ladderAndSnakeHtml.append("]");
        }
        ladderAndSnakeHtml.append(";");

        snakeAndLadderBoardList.add(snakeAndLadderHtml);
        snakeAndLadderBoardList.add(ladderAndSnakeHtml);

        return snakeAndLadderBoardList;
    }

    private static String getSqureColour(int position, int i)
    {

        if (i >= 5)
            i = i - 5;

        position = position + i;

        while (position >= 5)
        {
            position = position - 5;
        }

        if (position == 0)
            return "red";
        else if (position == 1) {
            return "purple";
        }
        else if (position == 2) {
            return "yellow";
        }
        else if (position == 3) {
            return "green";
        }
        else if (position == 4) {
            return "blue";
        }
        else
            return "orange";
    }

    public static String playerCoin(String backgroundColor, int numberOfPlayers)
    {
        StringBuilder coins = new StringBuilder();

        String colour = "yellow";

        for (int i = 1; i <= numberOfPlayers; i++)
        {
            if (i == 2) {
                colour = "#06f6f6";
            }
            if (i == 3) {
                colour = "green";
            }

            String clr = "color: " + colour + ";";

            coins.append(String.format("<i class=\"fa-solid fa-user\" style=\"%s\" id=\"%s\"></i>", clr, "coin_" + i));

        }

        return String.format(
                "<div id=\"%s\" class=\"snakeAndLadder\" style=\"%s\">1%s</div>", 1, backgroundColor,coins
        );
    }
}
