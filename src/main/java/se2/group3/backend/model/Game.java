package se2.group3.backend.model;

import se2.group3.backend.model.cards.ActionCard;
import se2.group3.backend.model.cards.CareerCard;
import se2.group3.backend.model.cards.HouseCard;

import java.util.List;
import java.util.Random;

public class Game {
    private List<Player> players;
    private Board board;
    private Deck<ActionCard> actionCardDeck;
    private Deck<CareerCard> careerCardDeck;
    private Deck<HouseCard> houseCardDeck;
    private int currentPlayerIndex;
    private Random spinnedNumber;


    public Game() {
        initializeDecks();
        currentPlayerIndex = 0; // Start with the first player
        spinnedNumber = new Random();
    }

    private void initializeDecks() {
        actionCardDeck = new Deck<>();
        // TODO: Add action cards to the deck
        // actionCardDeck.addCard(new ActionCard("Action name ", "Description of action " ));

        actionCardDeck.shuffle();

        careerCardDeck = new Deck<>();
        // TODO: Add career cards to the deck
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
