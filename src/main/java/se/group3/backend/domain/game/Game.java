package se.group3.backend.domain.game;

import se.group3.backend.domain.cards.ActionCard;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;
import se.group3.backend.domain.player.Player;
import se.group3.backend.repositories.ActionCardRepository;
import se.group3.backend.repositories.CareerCardRepository;
import se.group3.backend.repositories.CellRepository;
import se.group3.backend.repositories.HouseCardRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Game {
    private List<Player> players;
    private Board board;


    private final CareerCardRepository careerCardRepository;
    private final ActionCardRepository actionCardRepository;
    private final HouseCardRepository houseCardRepository;

    private final CellRepository cellRepository;

    private int currentPlayerIndex;
    private Random spinnedNumber;


    @Autowired
    public Game(CareerCardRepository careerCardRepository, ActionCardRepository actionCardRepository, HouseCardRepository houseCardRepository, CellRepository cellRepository) {
        this.careerCardRepository = careerCardRepository;
        this.actionCardRepository = actionCardRepository;
        this.houseCardRepository = houseCardRepository;
        this.cellRepository = cellRepository;
        currentPlayerIndex = 0; // Start with the first player
        spinnedNumber = new Random();
    }


    public void initializeBoard(){
        List<Cell> cells = cellRepository.findAll();
        board = new Board(cells);
        log.debug(board.toString());
        }


    public void startGame() {
        // Start the game, set up initial conditions, determine starting player, etc.
        throw new UnsupportedOperationException();
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // Move to the next player
        Player currentPlayer = players.get(currentPlayerIndex);
        int steps = spinSpinner(); // Spin the spinner to determine the number of steps
        board.movePlayer(currentPlayer, steps); // Move the player on the board
        if (checkWinCondition()) {
            // Handle game over condition
            throw new UnsupportedOperationException();
        }
    }

    private int spinSpinner() {
        // Simulate spinning the spinner to get a random number of steps
        return spinnedNumber.nextInt(10) + 1; // Generates a random number between 1 and 10
    }

    private boolean checkWinCondition() {
        // Check if a player has met the win condition
        // Implement the logic to check whether any player has won the game
        throw new UnsupportedOperationException();
    }
}
