package se2.group3.backend.networking.lobby;

import lombok.Getter;
import se2.group3.backend.dto.Player;

@Getter
public class Lobby {

    private final long id;
    private final Player host;

    public Lobby(long lobbyID, Player host) {
        this.id = lobbyID;
        this.host = host;

        //todo: host should be added automatically during creation
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

}
