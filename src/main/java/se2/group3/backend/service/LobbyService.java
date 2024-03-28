package se2.group3.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import se2.group3.backend.dto.LobbyDTO;
import se2.group3.backend.dto.PlayerDTO;
import se2.group3.backend.dto.mapper.LobbyMapper;
import se2.group3.backend.dto.mapper.PlayerMapper;
import se2.group3.backend.exceptions.SessionOperationException;
import se2.group3.backend.model.Player;
import se2.group3.backend.model.Lobby;
import se2.group3.backend.util.SessionUtil;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class LobbyService {

    private final AtomicLong idGenerator = new AtomicLong();
    private final ConcurrentHashMap<Long, Lobby> lobbyMap = new ConcurrentHashMap<>();

    /**
     * Creates a new {@link Lobby} with the specified {@link PlayerDTO} as a host and a unique id.
     * The information on the newly created {@link Lobby} is then returned in the form of a {@link LobbyDTO}
     * Should the player already be in a lobby
     * (which is determined based on the content of the session attribute "lobbyID"),
     * the session attribute has a different type or the session attributes are not accessible,
     * the method will return 'null'.
     * @param player The PlayerDTO which contains information on the hosting player.
     * @return LobbyDTO A LobbyDTO containing all information related to the new lobby or null.
     */
    public LobbyDTO createLobby(PlayerDTO player, SimpMessageHeaderAccessor headerAccessor) {
        Long sessionLobbyID;
        try {
            sessionLobbyID = SessionUtil.getLobbyID(headerAccessor);
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
            return null;
        }

        if(sessionLobbyID != null) {
            return null;
        }

        Player host = PlayerMapper.toPlayerModel(player);
        long lobbyID = idGenerator.getAndIncrement();
        Lobby newLobby = new Lobby(lobbyID, host);
        lobbyMap.put(lobbyID, newLobby);
        new Thread(newLobby).start();
        return LobbyMapper.toLobbyDTO(newLobby);
    }

    public Lobby joinLobby(long lobbyID, Player player) throws Exception {
        Lobby lobby = lobbyMap.get(lobbyID);
        if(lobby == null || lobby.isFull()) {
            return null;
        }

        Long sessionLobbyID;
        try {
            sessionLobbyID = SessionUtil.getLobbyID(headerAccessor);
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
            return null;
        }
        if(sessionLobbyID != null) {
            return null;
        }

        try {
            SessionUtil.putSessionAttribute(headerAccessor, "lobbyID", lobbyID);
        } catch (SessionOperationException e) {
            log.error(e.getMessage());
            return null;
        }

        Player player = PlayerMapper.toPlayerModel(playerDTO);
        lobby.addPlayer(player);
        return LobbyMapper.toLobbyDTO(lobby);
    }

    public LobbyDTO leaveLobby(SimpMessageHeaderAccessor headerAccessor) throws IllegalStateException, SessionOperationException {
        String sessionID = SessionUtil.getSessionID(headerAccessor);
        Long lobbyID = SessionUtil.getLobbyID(headerAccessor);

        if(lobbyID == null) {
            throw new IllegalStateException("Attempting to leave lobby, when player is not part of any lobby!");
        }
        Lobby lobby = lobbyMap.get(lobbyID);
        if(lobby == null) {
            throw new IllegalStateException("Lobby associated with session connection does not exist!");
        }

        lobby.removePlayer(sessionID);
        return LobbyMapper.toLobbyDTO(lobby);
    }

    public Lobby getLobby(long lobbyID) {
        return lobbyMap.get(lobbyID);
    }

    public Collection<Lobby> getAll() {
        return lobbyMap.values();
    }
}