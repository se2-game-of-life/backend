package se.group3.backend.domain.game;

import lombok.Getter;
import lombok.Setter;
import se.group3.backend.domain.cards.ActionCard;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;
import se.group3.backend.domain.cells.Cell;
import se.group3.backend.domain.player.Player;
import se.group3.backend.repositories.ActionCardRepository;
import se.group3.backend.repositories.CareerCardRepository;
import se.group3.backend.repositories.HouseCardRepository;
import se.group3.backend.services.CellService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

@Getter
@Setter
@Slf4j
public class Game {
    private Player[] players;
    private Board board;
    private Deck<ActionCard> actionCardDeck;
    private Deck<CareerCard> careerCardDeck;
    private Deck<HouseCard> houseCardDeck;

//    private final CellService cellService;

    private Player currentPlayer;
    private int spunNumber;

    public Game(Player[] players) {
        this.players = players;
//        this.careerCardRepository = careerCardRepository;
//        this.actionCardRepository = actionCardRepository;
//        this.houseCardRepository = houseCardRepository;
//        this.cellService = cellService;
    }

/*    public void initializeDecks() {
        List<ActionCard> actionCards = actionCardRepository.findAll();

        actionCardDeck = new Deck<>(actionCards);
        actionCardDeck.shuffle();

        List<CareerCard> careerCards = careerCardRepository.findAll();
        careerCardDeck = new Deck<>(careerCards);
        careerCardDeck.shuffle();

        List<HouseCard> houseCards = houseCardRepository.findAll();
        houseCardDeck = new Deck<>(houseCards);
        houseCardDeck.shuffle();
    }*/

//    public void initializeBoard(){
//        List<Cell> cells = cellService.getAllCells();
//        board = new Board(cells);
//        log.debug(board.toString());
//    }


    public void startGame() {
        // Start the game, set up initial conditions, determine starting player, etc.
        throw new UnsupportedOperationException();
    }

//    public void nextTurn() {
////        currentPlayer = (currentPlayer + 1) % players.size(); // Move to the next player
////        Player currentPlayer = players.get(this.currentPlayer);
////        int steps = spinSpinner(); // Spin the spinner to determine the number of steps
////        board.movePlayer(currentPlayer, steps); // Move the player on the board
////        if (checkWinCondition()) {
////            // Handle game over condition
////            throw new UnsupportedOperationException();
////        }
//    }

    public int spinSpinner() {
        // Simulate spinning the spinner to get a random number of steps
        return spunNumber = new Random().nextInt(10) + 1; // Generates a random number between 1 and 10
    }

    private boolean checkWinCondition() {
        // Check if a player has met the win condition
        // Implement the logic to check whether any player has won the game
        throw new UnsupportedOperationException();
    }

}
