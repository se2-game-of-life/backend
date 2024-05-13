package se.group3.backend.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import se.group3.backend.domain.cards.Card;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
public class Lobby {

    private final long id;
    private static final Short MAXIMUM_PLAYER_COUNT = 4;
    private final List<Player> players;
    private Player currentPlayer;
    private boolean hasDecision;
    private List<Card> cards;
    private int spunNumber;
    private boolean hasStarted;

    public Lobby(long lobbyID, Player host) {
        this.id = lobbyID;
        players = new ArrayList<>(MAXIMUM_PLAYER_COUNT);
        currentPlayer = host;
        cards = new ArrayList<>(2);
        hasStarted = false;
        hasDecision = false;
        spunNumber = 0;

        addPlayer(host);
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
