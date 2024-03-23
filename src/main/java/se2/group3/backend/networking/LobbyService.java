package se2.group3.backend.networking;

import org.springframework.stereotype.Service;
import se2.group3.backend.dto.Player;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LobbyService {

    private final AtomicLong idGenerator = new AtomicLong();
    private final ConcurrentHashMap<Long, Lobby> lobbyMap = new ConcurrentHashMap<>();

    public Lobby createLobby(Player host) {
        long lobbyID = idGenerator.getAndIncrement();
        Lobby newLobby = new Lobby(lobbyID, host);
        lobbyMap.put(lobbyID, newLobby);
        return newLobby;
    }

    public Lobby joinLobby(long lobbyID, Player player) throws Exception {
        Lobby lobby = lobbyMap.get(lobbyID);

        if(lobby == null) {
            throw new Exception("Lobby could not be found!");
        }

        //todo: need to finish the player add stuff and also change the way the lobby controller handles this
//        lobby.addPlayer(player);

        return lobby;
    }

    public boolean leaveLobby(long lobbyID, Player player) {
        Lobby lobby = lobbyMap.get(lobbyID);
        if(lobby == null) {
            return false;
        }
        lobby.removePlayer(player); //this might need error handling later
        return true;
    }

    public Lobby getLobby(long lobbyID) {
        return lobbyMap.get(lobbyID);
    }

    public Collection<Lobby> getAll() {
        return lobbyMap.values();
    }
}
