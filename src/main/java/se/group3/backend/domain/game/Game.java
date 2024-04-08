package se.group3.backend.domain.game;

import se.group3.backend.domain.cards.ActionCard;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;
import se.group3.backend.domain.cells.Cell;
import se.group3.backend.domain.player.Player;
import se.group3.backend.repositories.ActionCardRepository;
import se.group3.backend.repositories.CareerCardRepository;
import se.group3.backend.repositories.HouseCardRepository;
import se.group3.backend.services.CellService;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Game {
    private List<Player> players;
    private Board board;
    private Deck<ActionCard> actionCardDeck;
    private Deck<CareerCard> careerCardDeck;
    private Deck<HouseCard> houseCardDeck;

    private final CareerCardRepository careerCardRepository;
    private final ActionCardRepository actionCardRepository;
    private final HouseCardRepository houseCardRepository;

    private final CellService cellService;

    private int currentPlayerIndex;
    private Random spinnedNumber;


    @Autowired
    public Game(CareerCardRepository careerCardRepository, ActionCardRepository actionCardRepository, HouseCardRepository houseCardRepository, CellService cellService) {
        this.careerCardRepository = careerCardRepository;
        this.actionCardRepository = actionCardRepository;
        this.houseCardRepository = houseCardRepository;
        this.cellService = cellService;
        currentPlayerIndex = 0; // Start with the first player
        spinnedNumber = new Random();
    }

    public void initializeDecks() {
        List<ActionCard> actionCards = actionCardRepository.findAll();
        /*
        for (ActionCard card : actionCards) {
            System.out.println(card);
        }*/
        actionCardDeck = new Deck<>(actionCards);
        actionCardDeck.shuffle();

        List<CareerCard> careerCards = careerCardRepository.findAll();
        careerCardDeck = new Deck<>(careerCards);
        careerCardDeck.shuffle();

        List<HouseCard> houseCards = houseCardRepository.findAll();
        houseCardDeck = new Deck<>(houseCards);
        houseCardDeck.shuffle();
    }

    public void initializeBoard(){
        List<Cell> cells = cellService.getAllCells();
        board = new Board(cells);
        System.out.println(board);
        }


    public void startGame() {
        // Start the game, set up initial conditions, determine starting player, etc.
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // Move to the next player
        Player currentPlayer = players.get(currentPlayerIndex);
        int steps = spinSpinner(); // Spin the spinner to determine the number of steps
        board.movePlayer(currentPlayer, steps); // Move the player on the board
        if (checkWinCondition()) {
            // Handle game over condition
        }
    }

    private int spinSpinner() {
        // Simulate spinning the spinner to get a random number of steps
        return spinnedNumber.nextInt(10) + 1; // Generates a random number between 1 and 10
    }

    private boolean checkWinCondition() {
        // Check if a player has met the win condition
        // Implement the logic to check whether any player has won the game
        return false;
    }
}
