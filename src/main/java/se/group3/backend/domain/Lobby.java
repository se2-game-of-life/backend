package se.group3.backend.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.group3.backend.domain.cards.Card;

import java.util.*;

@Slf4j
@Getter
@Setter
@Document(collection = "lobby")
public class Lobby {

    @Id
    private long lobbyID;
    private static final Short MAXIMUM_PLAYER_COUNT = 4;
    private List<Player> players;
    private Player currentPlayer;
    private boolean hasDecision;
    private List<Card> cards;
    private int spunNumber;
    private boolean hasStarted;

    private ArrayList<Player> queue;

    public Lobby(Long lobbyID, Player currentPlayer) {
        this.lobbyID = lobbyID;
        this.currentPlayer = currentPlayer;
        players = new ArrayList<>(MAXIMUM_PLAYER_COUNT);
        queue = new ArrayList<>(MAXIMUM_PLAYER_COUNT);
        cards = new ArrayList<>(2);
        hasStarted = false;
        hasDecision = false;
        spunNumber = 0;

        addPlayer(currentPlayer);
    }

    public void addPlayer(Player player) {
       players.add(player);
       queue.add(player);
    }

    public void removePlayer(String uuid) {
        players.removeIf(player -> player.getPlayerUUID().equals(uuid));
    }

    public void nextPlayer() {
        queue.remove(0);
        queue.add(currentPlayer);
        currentPlayer = queue.get(0);
    }

    public boolean isFull() {
        return players.size() >= MAXIMUM_PLAYER_COUNT;
    }

    public void addCard(Card card) {
        cards.add(card);
    }
}
