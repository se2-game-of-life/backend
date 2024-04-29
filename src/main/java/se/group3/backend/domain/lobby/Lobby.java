package se.group3.backend.domain.lobby;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import se.group3.backend.domain.game.Game;
import se.group3.backend.domain.player.Player;

@Slf4j
@Getter
public class Lobby {

    private volatile boolean running;

    private final long id;
    private static final short MAXIMUM_PLAYER_COUNT = 4;
    private short currentPlayerCount = 0;
    private final Player host;
    private final Player[] players = new Player[MAXIMUM_PLAYER_COUNT];
    private short currentPlayerIndex;
    private final Game game;

    public Lobby(long lobbyID, Player host) {
        this.id = lobbyID;
        this.host = host;
        game = new Game(players);
        addPlayer(host);
        this.currentPlayerIndex = 0;
    }

    public void nextTurn(boolean goAgain) {
        game.setCurrentPlayer(players[currentPlayerIndex++]);
        if(!goAgain) currentPlayerIndex++;
        if(currentPlayerIndex > MAXIMUM_PLAYER_COUNT) currentPlayerIndex = 0;
    }

    public void addPlayer(Player player) {
        players[currentPlayerCount] = player;
        currentPlayerCount++;
    }

    public void removePlayer(String uuid) {
        // if the number of players <= 0 the session should be closed,
        // remove player logic
        // if player is host, move host to another player at random
        currentPlayerCount--;
        log.debug(uuid);
    }

    public boolean isFull() {
        return currentPlayerCount >= MAXIMUM_PLAYER_COUNT;
    }

    public void closeLobby() {
        running = false;
    }

}
