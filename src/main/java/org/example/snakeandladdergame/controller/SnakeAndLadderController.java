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
import static org.example.snakeandladdergame.utils.Utils.playerCoin;

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

    private static boolean gameOver = false;

    private static int numberOfPlayersConst = 2;

    private static List<StringBuilder> stringBuilders = new ArrayList<>();

    @GetMapping("/")
    public ModelAndView home()
    {
        gameOver = false;
        numberOfPlayersConst = 2;
        return new ModelAndView("index");
    }

    @GetMapping("/selectNumberOfPlayers")
    public ModelAndView selectNumberOfPlayers(@RequestParam int numberOfPlayers, Model model)
    {
        numberOfPlayersConst = numberOfPlayers;
        initializePlayerAndBoard(numberOfPlayers);
        model.addAttribute("snakeAndLadderHtml", stringBuilders.get(0).toString());
        model.addAttribute("ladderAndSnakeHtml", stringBuilders.get(1).toString());
        return new ModelAndView("snakeAndLadder");
    }


    @GetMapping("/rotateDize")
    public Player rotateDize(@RequestParam int result,@RequestParam int playerNum)
    {
        if (gameOver)
        {
            Player player = new  Player();
            player.setPlayerPosition(100);
            return player;
        }

        Player player = Game.rotateDize(playerNum, result);

        if (player == null)
        {
            System.out.println("player is null");
            return new Player();
        }

        return player;
    }

    @RequestMapping(value = "/GameOver", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView gameOver(@RequestParam String player, Model model)
    {
        gameOver = true;
        model.addAttribute("player",  player);
        return new ModelAndView("winningPage");
    }

    @RequestMapping("/restart")
    private ModelAndView restartGame(Model model)
    {
        gameOver = false;
        initializePlayerAndBoard(numberOfPlayersConst);
        model.addAttribute("snakeAndLadderHtml", stringBuilders.get(0).toString());
        model.addAttribute("ladderAndSnakeHtml", stringBuilders.get(1).toString());
        return new ModelAndView("snakeAndLadder");
    }

    private static void initializePlayerAndBoard(int numberOfPlayers) {
        initailizePlayers(numberOfPlayers);
        SnakeAndLadder[][] snakeAndLadders = startGame(5, 5);
        stringBuilders = Utils.loadSnakeAndLadderBoard(snakeAndLadders, numberOfPlayers);
    }

}

