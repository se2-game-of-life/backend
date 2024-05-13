package se.group3.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import se.group3.backend.domain.cards.Card;

import java.util.ArrayList;
import java.util.List;

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

    public Lobby(Long lobbyID, Player currentPlayer) {
        this.lobbyID = lobbyID;
        this.currentPlayer = currentPlayer;
        players = new ArrayList<>(MAXIMUM_PLAYER_COUNT);
        cards = new ArrayList<>(2);
        hasStarted = false;
        hasDecision = false;
        spunNumber = 0;

        addPlayer(currentPlayer);
    }

    public void addPlayer(Player player) {
       players.add(player);
    }

    public void removePlayer(String uuid) {
        players.removeIf(player -> player.getPlayerUUID().equals(uuid));
    }

    public boolean isFull() {
        return players.size() >= MAXIMUM_PLAYER_COUNT;
    }
}
