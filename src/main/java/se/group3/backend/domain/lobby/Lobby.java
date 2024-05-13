package se.group3.backend.domain.lobby;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import se.group3.backend.domain.player.Player;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class Lobby {

    private final long id;
    private static final Short MAXIMUM_PLAYER_COUNT = 4;
    private final List<Player> players = new ArrayList<>(MAXIMUM_PLAYER_COUNT);

    public Lobby(long lobbyID, Player host) {
        this.id = lobbyID;
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
