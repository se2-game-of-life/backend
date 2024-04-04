package se2.group3.backend.model;

import se2.group3.backend.model.cards.ActionCard;
import se2.group3.backend.model.cards.CareerCard;
import se2.group3.backend.model.cards.HouseCard;
import se2.group3.backend.repo.CareerCardRepository;

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
    private final CareerCardRepository careerCardRepository;
    private Deck<HouseCard> houseCardDeck;
    private int currentPlayerIndex;
    private Random spinnedNumber;


    @Autowired
    public Game(CareerCardRepository careerCardRepository) {
        this.careerCardRepository = careerCardRepository;
        currentPlayerIndex = 0; // Start with the first player
        spinnedNumber = new Random();
    }

    public void initializeDecks() {
        actionCardDeck = new Deck<>();
        // TODO: Add action cards to the deck
        actionCardDeck.shuffle();

        List<CareerCard> careerCards = careerCardRepository.findAll();
        // Print the contents of the career card deck
        for (CareerCard card : careerCards) {
            System.out.println(card);
        }
        careerCardDeck = new Deck<>(careerCards);
        careerCardDeck.shuffle();

        houseCardDeck = new Deck<>();
        // TODO: Add house cards to the deck
        houseCardDeck.shuffle();
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
