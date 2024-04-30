package se.group3.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import se.group3.backend.controller.LobbyController;
import se.group3.backend.domain.player.Player;
import se.group3.backend.dto.GameStateDTO;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.dto.mapper.LobbyMapper;
import se.group3.backend.exceptions.SessionOperationException;
import se.group3.backend.domain.lobby.Lobby;
import se.group3.backend.util.SessionUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static se.group3.backend.dto.mapper.PlayerMapper.mapDTOToPlayer;

@Slf4j
@Service
public class LobbyService {

    private final AtomicLong idGenerator = new AtomicLong();
    private final ConcurrentHashMap<Long, Lobby> lobbyMap = new ConcurrentHashMap<>();

    /**
     * Creates a new lobby with the host player as a member.
     * This method takes a host and the headerAccessor to verify that the player is not connected to another lobby.
     * A new lobby is then created, added to the lobby list, started (as lobbies are running on separate threads)
     * and returned to the user in a LobbyDTO.
     * @param player The host player, who creates the lobby.
     * @param headerAccessor Stores information about the lobby the player is connected to.
     * @return A {@link LobbyDTO} containing the state of the lobby.
     * @throws IllegalStateException if the player is already in another lobby.
     * @throws SessionOperationException if the lobbyID from the headerAccessor cant be extracted.
     */
    public LobbyDTO createLobby(PlayerDTO player, SimpMessageHeaderAccessor headerAccessor) throws IllegalStateException, SessionOperationException {
        Long sessionLobbyID = SessionUtil.getLobbyID(headerAccessor);
        if(sessionLobbyID != null) throw new IllegalStateException("This player is already in another Lobby!");

        Player host = mapDTOToPlayer(player);
        long lobbyID = idGenerator.getAndIncrement();
        Lobby newLobby = new Lobby(lobbyID, host);
        SessionUtil.putSessionAttribute(headerAccessor, "lobbyID", newLobby.getId());
        lobbyMap.put(lobbyID, newLobby);
        return LobbyMapper.toLobbyDTO(newLobby);
    }

    /**
     * Business Logic for join lobby requests, which are handled in the {@link LobbyController}.
     * It takes a lobbyID for the lobby the user wants to join, the player data of the user and the header accessor
     * of the connection, as the lobby information of the player is stored there, under 'lobbyID'.
     * If the player is already in another lobby (indicated by the presence of another lobbyID in the header),
     * the method will throw an IllegalStateException.
     * It will also throw an IllegalStateException if the lobby is full or the lobby doesn't exist.
     * If the lobbyID can't be saved in the session attributes, the method will throw a SessionOperationException.
     * @param lobbyID The lobbyID of the lobby the player wants to join into.
     * @param playerDTO The DTO contains the data of the player that wants to join.
     * @param headerAccessor Stores information about the lobby the player is connected to.
     * @return A {@link LobbyDTO} containing the data of the lobby.
     * @throws IllegalStateException If the lobby is full, the player is already in another lobby or the lobby doesn't exist.
     * @throws SessionOperationException If writing or reading the lobbyID from the headerAccessor fails.
     */
    public LobbyDTO joinLobby(long lobbyID, PlayerDTO playerDTO, SimpMessageHeaderAccessor headerAccessor) throws IllegalStateException, SessionOperationException {
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

    /**
     * Business Logic for the lobby leave requests, which is called from the {@link LobbyController}.
     * Takes in a headerAccessor, which is needed to get information on the current lobby of the player (if he has one).
     * In case the player is not in a lobby at the time of the request or the lobby the player is in doesn't exist anymore,
     * the method throws an IllegalArgumentException.
     * If the interaction with the header accessor fails, a SessionOperationException is thrown.
     * @param headerAccessor which is needed to get lobbyID and sessionID.
     * @return A {@link LobbyDTO} which represents the new state of the lobby after the player leaves.
     * @throws IllegalStateException If the player is not in a lobby or the lobby which the player is in doesn't exist.
     * @throws SessionOperationException If the operations to get information from the session header fail.
     */
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

    public GameStateDTO startGame(long lobbyID) {
        Lobby lobby = lobbyMap.get(lobbyID);
        if(lobby == null) throw new IllegalStateException("The lobby doesn't exist!");

        //todo: get game state from lobby ()
        return null;
    }

    public Lobby getLobby(long lobbyID) {
        return lobbyMap.get(lobbyID);
    }
}
