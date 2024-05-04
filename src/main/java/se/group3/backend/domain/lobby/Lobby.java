package se.group3.backend.domain.lobby;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import se.group3.backend.domain.player.Player;

@Slf4j
@Getter
public class Lobby implements Runnable {

    private boolean running;

    private final long id;
    private static final Short MAXIMUM_PLAYER_COUNT = 4;
    private Short currentPlayerCount = 0;
    private final Player host;
    private final Player[] players = new Player[MAXIMUM_PLAYER_COUNT];

    public Lobby(long lobbyID, Player host) {
        this.id = lobbyID;
        this.host = host;
        addPlayer(host);
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            //add game logic
            running = false;
        }
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
