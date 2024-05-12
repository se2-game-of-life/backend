package se.group3.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.player.Player;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.dto.mapper.LobbyMapper;
import se.group3.backend.exceptions.SessionOperationException;
import se.group3.backend.domain.lobby.Lobby;
import se.group3.backend.repositories.LobbyRepository;

import java.util.concurrent.atomic.AtomicLong;

import static se.group3.backend.dto.mapper.PlayerMapper.mapDTOToPlayer;

@Slf4j
@Service
public class LobbyService {

    private final AtomicLong idGenerator = new AtomicLong();
    private final LobbyRepository lobbyRepository;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }

    public LobbyDTO createLobby(PlayerDTO player) throws IllegalStateException {
        Long sessionLobbyID = SessionUtil.getLobbyID(headerAccessor);
        if(sessionLobbyID != null) throw new IllegalStateException("This player is already in another Lobby!");

        Player host = mapDTOToPlayer(player);
        long lobbyID = idGenerator.getAndIncrement();
        Lobby newLobby = new Lobby(lobbyID, host);
        SessionUtil.putSessionAttribute(headerAccessor, "lobbyID", newLobby.getId());
        lobbyMap.put(lobbyID, newLobby);
        new Thread(newLobby).start();
        return LobbyMapper.toLobbyDTO(newLobby);
    }

    public LobbyDTO joinLobby(long lobbyID, PlayerDTO playerDTO) throws IllegalStateException {
        Lobby lobby = lobbyMap.get(lobbyID);
        if(lobby == null) throw new IllegalStateException("The lobby doesn't exist!");
        if(lobby.isFull()) throw new IllegalStateException("The lobby is full!");

        Long sessionLobbyID = SessionUtil.getLobbyID(headerAccessor);
        if(sessionLobbyID != null) throw new IllegalStateException("This player is already in another Lobby!");

        SessionUtil.putSessionAttribute(headerAccessor, "lobbyID", lobbyID);
        Player player = mapDTOToPlayer(playerDTO);
        lobby.addPlayer(player);
        return LobbyMapper.toLobbyDTO(lobby);
    }

    public LobbyDTO leaveLobby(SimpMessageHeaderAccessor headerAccessor) throws IllegalStateException, SessionOperationException {
        String uuid = SessionUtil.getUUID(headerAccessor);
        Long lobbyID = SessionUtil.getLobbyID(headerAccessor);

        if(lobbyID == null) {
            throw new IllegalStateException("Attempting to leave lobby, when player is not part of any lobby!");
        }
        Lobby lobby = getLobby(lobbyID);
        if(lobby == null) {
            throw new IllegalStateException("Lobby associated with session connection does not exist!");
        }

        lobby.removePlayer(uuid);
        return LobbyMapper.toLobbyDTO(lobby);
    }

    public Lobby getLobby(long lobbyID) {
        return lobbyMap.get(lobbyID);
    }

    public void startLobby(long uuid) {
        throw new UnsupportedOperationException();
    }
}
