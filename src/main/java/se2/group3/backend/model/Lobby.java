package se2.group3.backend.model;

import lombok.Getter;

@Getter
public class Lobby implements Runnable {

    private boolean running;

    private final long id;
    private final Short MAX_PLAYERS = 4;
    private Short currentPlayerCount = 0;
    private final Player host;
    private final Player[] players = new Player[MAX_PLAYERS];

    public Lobby(long lobbyID, Player host) {
        this.id = lobbyID;
        this.host = host;
        this.players[0] = host;
        currentPlayerCount++;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            //add game logic
        }
    }

    public boolean addPlayer(Player player) {
        //add player Logic
        currentPlayerCount++;
        return true;
    }

    public void removePlayer(String playerID) {
        // if the number of players <= 0 the session should be closed,
        // remove player logic
        // if player is host, move host to another player at random
        currentPlayerCount--;
    }

    public boolean isFull() {
        return currentPlayerCount >= MAX_PLAYERS;
    }

    public void closeLobby() {
        running = false;
    }

}
