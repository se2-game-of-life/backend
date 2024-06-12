package se.group3.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
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
    private int playersTurnCounter;

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
        playersTurnCounter = 0;
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
        updateCounter();
        queue.add(players.get(playersTurnCounter));
        currentPlayer = queue.get(0);
    }

    public boolean isFull() {
        return players.size() >= MAXIMUM_PLAYER_COUNT;
    }

    private void updateCounter(){
        if(playersTurnCounter < players.size()-1){
            playersTurnCounter++;
        } else{
            playersTurnCounter = 0;
        }

    }

    public void addCard(Card card) {
        cards.add(card);
    }
}
