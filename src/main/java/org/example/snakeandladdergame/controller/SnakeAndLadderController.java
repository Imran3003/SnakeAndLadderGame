package org.example.snakeandladdergame.controller;

import org.example.snakeandladdergame.impl.Game;
import org.example.snakeandladdergame.models.Player;
import org.example.snakeandladdergame.models.SnakeAndLadder;
import org.example.snakeandladdergame.utils.Utils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.example.snakeandladdergame.impl.Game.initailizePlayers;
import static org.example.snakeandladdergame.impl.Game.startGame;

/**
 * SnakeAndLadderController.java
 *
 * @author Mohamed Subaideen Imran A (mohamedsubaideenimran@nmsworks.co.in)
 * @module org.example.snakeAndLadder.controller
 * @created Jul 18, 2024
 */

@RestController
public class SnakeAndLadderController
{
    private static boolean gameStarted = false;

    private static List<StringBuilder> stringBuilders = new ArrayList<>();

    @GetMapping("/")
    public ModelAndView home()
    {
        System.out.println("called home ...");
        return new ModelAndView("index");
    }

    @GetMapping("/selectNumberOfPlayers")
    public ModelAndView selectNumberOfPlayers(@RequestParam int numberOfPlayers, Model model)
    {
        initailizePlayers(numberOfPlayers);
        if (!gameStarted)
        {
            SnakeAndLadder[][] snakeAndLadders = startGame(5, 5);
            stringBuilders = Utils.loadSnakeAndLadderBoard(snakeAndLadders,numberOfPlayers);
        }
        model.addAttribute("snakeAndLadderHtml", stringBuilders.get(0).toString());
        model.addAttribute("ladderAndSnakeHtml", stringBuilders.get(1).toString());
        gameStarted = true;
        return new ModelAndView("snakeAndLadder");
    }

    @GetMapping("/rotateDize")
    public Player rotateDize(@RequestParam int result,@RequestParam int playerNum)
    {
        System.out.println("result got = " + result + " playerNum = " + playerNum);

        Player player = Game.rotateDize(playerNum, result);

        if (player == null)
        {
            System.out.println("player is null");
            return new Player();
        }

        if (player.getPlayerPosition() > 99)
        {
            System.out.println("\n\n ##########################################################################################");
            System.out.println(" #                          $$$$ < * Game over * > $$$$                                   #");
            System.out.println(" ##########################################################################################\n \n");
            System.out.println(" ( $$##$$ ) < " + player.getPlayerName() + " is winner !!! > ( $$##$$ )");
            return player;
        }
        System.out.println(" ### player " + playerNum + " :: From = " + player.getPlayerFromPosition() + " :: ToPostion =  " + player.getPlayerPosition());
        return player;
    }

    @RequestMapping(value = "/GameOver", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView gameOver(@RequestParam String player, Model model) {
        model.addAttribute("player", "player_" + player + "win");
        return new ModelAndView("winningPage");
    }

    @RequestMapping("/restart")
    private ModelAndView restartGame()
    {
        gameStarted = false;
        return new ModelAndView("index");
    }
}
