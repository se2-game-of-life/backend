package se2.group3.backend;

import lombok.Getter;

@Getter
public class Session {

    private final long id;
    private final Player host;

    public Session(long sessionID, Player host) {
        this.id = sessionID;
        this.host = host;

        //todo: host should be added automatically during creation
    }

    public boolean addPlayer(Player player) {
        //todo: add player Logic
        return true;
    }

    public boolean removePlayer(Player player) {
        // if number of players <= 0 the session should be closed
        //todo: remove player logic
        return true;
    }

}
