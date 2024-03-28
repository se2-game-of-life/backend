package se2.group3.backend.model;

import lombok.Getter;

@Getter
public class Lobby implements Runnable {

    private boolean running;

    private final long id;
    private final Short MAX_PLAYERS = 4;
    private final Player host;
    private final Player[] players = new Player[MAX_PLAYERS];

    public Lobby(long lobbyID, Player host) {
        this.id = lobbyID;
        this.host = host;
        this.players[0] = host;

        //todo: host should be added automatically during creation
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            //todo: add game logic
        }
    }

    public boolean addPlayer(Player player) {
        //todo: add player Logic
        return true;
    }

    public void removePlayer(String playerID) {
        // if number of players <= 0 the session should be closed
        //todo: remove player logic
        //todo: if player is host, move host to another player at random
    }

    public boolean isFull() {
        return players.length >= MAX_PLAYERS;
    }

    public void closeLobby() {
        running = false;
    }

}