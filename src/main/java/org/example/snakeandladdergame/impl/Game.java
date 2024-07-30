package org.example.snakeandladdergame.impl;

import org.example.snakeandladdergame.models.Player;
import org.example.snakeandladdergame.models.SnakeAndLadder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.snakeandladdergame.utils.Utils.generateRandomIndex;

/**
 * Game.java
 *
 * @author Mohamed Subaideen Imran A (mohamedsubaideenimran@nmsworks.co.in)
 * @module org.example.snakeandladdergame.impl
 * @created Jul 18, 2024
 */
public class Game
{
    private static  SnakeAndLadder[][] snakeAndLadders = new SnakeAndLadder[10][10];

    private static  List<Player> players = new ArrayList<>();

    public static SnakeAndLadder[][] startGame(int laddersCount,int snakeCount)
    {
        if (snakeAndLadders.length == 0)
            snakeAndLadders  = new SnakeAndLadder[10][10];

        generateSnakeAndLadderBoard(laddersCount,snakeCount);
        return snakeAndLadders;
    }

    public static Player rotateDize(int playerNo, int dizeNum)
    {
        Player player = players.get(playerNo - 1);

        int playerPosition = player.getPlayerPosition();

        if (playerPosition + dizeNum > 100)
        {
            System.out.println(" *** You can't move because your position = " + playerPosition + " and you got :: " + dizeNum + " ***");
            return null;
        }

        int[] cordinatesForPosition = getCordinatesForToPosition(playerPosition + dizeNum);

        SnakeAndLadder snakeAndLadder = snakeAndLadders[cordinatesForPosition[0]][cordinatesForPosition[1]];

        player.setPlayerFromPosition(playerPosition);

        if (snakeAndLadder.isSnakeIsPresent())
        {
            System.out.println(" $$$$$$$ Snake From " + snakeAndLadder.getPosition() + " To :: " + snakeAndLadder.getToPosition());
            player.setPlayerPosition(snakeAndLadder.getToPosition());
        }
        else if (snakeAndLadder.isLadderIsPresent())
        {
            System.out.println(" ####### Ladder From " + snakeAndLadder.getPosition() + " To :: " + snakeAndLadder.getToPosition());
            player.setPlayerPosition(snakeAndLadder.getToPosition());
        }
        else
        {
            player.setPlayerPosition(player.getPlayerPosition() + dizeNum);
        }

        return player;
    }

    public static int[] getCordinatesForToPosition(int playerToPosition)
    {
        if (playerToPosition >= 100) {
           return new int[]{0,0};
        }

        // Calculate the row
        int row = (playerToPosition % 10 == 0)  ? 9- (playerToPosition - 1)/10 : 9 - (playerToPosition) / 10 ;

        // Calculate the column
//        int column = row % 2 !=0 ? (playerToPosition - 1) % 10 : 10 - ((playerToPosition % 10) == 0 ? 1 : playerToPosition % 10);

        int column;

        if (row % 2 == 0)
        {
             column = 9 - (playerToPosition -1) % 10;
        }
        else
        {
            column = (playerToPosition - 1) % 10;
        }

        return new int[]{row, column};
    }

    public static void initailizePlayers(int noOfPlayers)
    {
        if(!players.isEmpty())
            players.clear();

        for (int i = 1 ; i <= noOfPlayers ; i++)
        {
            Player player = new Player();
            player.setPlayerName("Player_" + i);
            player.setPlayerPosition(1);
            player.setPlayerFromPosition(1);
            players.add(player);
        }
    }

    public static void generateSnakeAndLadderBoard(int laddersCount, int snakeCount)
    {
        int position = 100;

        for(int i = 0; i < 10; i++)
        {

            if (i % 2 == 0)
            {
                for(int j = 0; j < 10; j++)
                {
                    SnakeAndLadder s = new SnakeAndLadder();
                    s.setPosition(position);
                    snakeAndLadders[i][j] = s;
                    position--;
                }
            }
            else
            {
                for(int j = 9; j >= 0; j--)
                {
                    SnakeAndLadder s = new SnakeAndLadder();
                    s.setPosition(position);
                    snakeAndLadders[i][j] = s;
                    position--;
                }
            }
        }

        setLadders(laddersCount);
        setSnakes(snakeCount);
    }

    private static void setLadders(int laddersCount)
    {
        int lc = 0;
        while (true) {
            int randomPos = generateRandomIndex(1,9);
            int fp = randomPos == 9 ? 1 : 0;
            int randomPos1 = generateRandomIndex(fp,9);
            SnakeAndLadder snakeAndLadderPiece = snakeAndLadders[randomPos][randomPos1];
            boolean ladderIsPresent = snakeAndLadderPiece.isLadderIsPresent();

            if (!ladderIsPresent || snakeAndLadderPiece.getToPosition() == 0)
            {
                snakeAndLadderPiece.setLadderIsPresent(true);

                int randomCount;
                int randomCount1;

                do
                {
                    randomCount = randomPos > 1 ? generateRandomIndex(0,randomPos - 1) : 0;
                    randomCount1 = generateRandomIndex(0, 9);
                } while (snakeAndLadders[randomCount][randomCount1].isLadderIsPresent() || snakeAndLadders[randomPos][randomCount1].getToPosition() == 100);

                SnakeAndLadder gotSnL = snakeAndLadders[randomCount][randomCount1];
                snakeAndLadderPiece.setToPosition(gotSnL.getPosition());
                lc++;
            }

            if (lc == laddersCount)
                break;
        }
    }

    private static void setSnakes(int snakeCount)
    {
        int sc = 0;

        while (true) {
            int randomPos = generateRandomIndex(0, 8);
            int fp = randomPos == 0 ? 1 : 0;
            int randomPos1 = generateRandomIndex(fp , 9);
            SnakeAndLadder snakeAndLadderPiece = snakeAndLadders[randomPos][randomPos1];
            boolean ladderIsPresent = snakeAndLadderPiece.isLadderIsPresent();

            if (!ladderIsPresent && !snakeAndLadderPiece.isSnakeIsPresent()) {
                snakeAndLadderPiece.setSnakeIsPresent(true);

                int randomCount;
                int randomCount1;

                do {
                    int tp = randomPos + 1;
                    randomCount = generateRandomIndex(tp, 9);
                    randomCount1 = generateRandomIndex(1, 9);
                } while (snakeAndLadders[randomCount][randomCount1].isSnakeIsPresent());

                SnakeAndLadder gotSnL = snakeAndLadders[randomCount][randomCount1];
                snakeAndLadderPiece.setToPosition(gotSnL.getPosition());
                sc++;

            }

            if (sc == snakeCount)
                break;
        }
    }

}
