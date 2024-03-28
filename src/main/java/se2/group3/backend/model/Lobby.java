package se2.group3.backend.model;

import lombok.Getter;

@Getter
public class Lobby implements Runnable {

    private boolean running;

    private final long id;
    private final Player host;

    public Lobby(long lobbyID, Player host) {
        this.id = lobbyID;
        this.host = host;

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

    public void removePlayer(Player player) {
        // if number of players <= 0 the session should be closed
        //todo: remove player logic
        //todo: if player is host, move host to another player at random
    }

    public void closeLobby() {
        running = false;
    }

}
