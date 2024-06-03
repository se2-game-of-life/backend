package se.group3.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.Player;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.mapper.LobbyMapper;
import se.group3.backend.domain.Lobby;
import se.group3.backend.repositories.LobbyRepository;
import se.group3.backend.repositories.PlayerRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class LobbyService {

    private final AtomicLong idGenerator = new AtomicLong();
    private final LobbyRepository lobbyRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepository, PlayerRepository playerRepository) {
        this.lobbyRepository = lobbyRepository;
        this.playerRepository = playerRepository;
    }

    public LobbyDTO createLobby(String playerUUID, String playerName) throws IllegalStateException {

        Optional<Player> playerOptional = playerRepository.findById(playerUUID);
        if(playerOptional.isPresent()) {
            if(playerOptional.get().getLobbyID() != null) throw new IllegalStateException("Player already in another lobby!");
            playerRepository.deleteById(playerUUID);
        }

        long lobbyID = idGenerator.incrementAndGet();

        if (lobbyRepository.existsById(lobbyID)) {
            lobbyRepository.deleteById(lobbyID);
        }

        Player player = new Player(playerUUID, playerName.substring(1, playerName.length() - 1));
        player.setLobbyID(lobbyID);
        Lobby lobby = new Lobby(lobbyID, player);
        lobbyRepository.insert(lobby);
        playerRepository.insert(player);

        return LobbyMapper.toLobbyDTO(lobby);
    }

    public LobbyDTO joinLobby(long lobbyID, String playerUUID, String playerName) throws IllegalStateException {
        Optional<Player> playerOptional = playerRepository.findById(playerUUID);
        if(playerOptional.isPresent()) {
            if(playerOptional.get().getLobbyID() != null) throw new IllegalStateException("Player already in another lobby!");
            playerRepository.deleteById(playerUUID);
        }

        Player player = new Player(playerUUID, playerName);
        Optional<Lobby> lobbyOptional = lobbyRepository.findById(lobbyID);
        if(lobbyOptional.isPresent()) {
            Lobby lobby = lobbyOptional.get();
            if(lobby.isFull()) throw new IllegalStateException("The lobby is full!");
            player.setLobbyID(lobbyID);
            lobby.addPlayer(player);
            lobbyRepository.save(lobby);
            playerRepository.insert(player);
            return LobbyMapper.toLobbyDTO(lobby);
        }
        throw new IllegalStateException("The lobby doesn't exist!");
    }

    public LobbyDTO leaveLobby(String playerUUID) throws IllegalStateException {
        Optional<Player> playerOptional = playerRepository.findById(playerUUID);
        if(playerOptional.isPresent()) {
            Player player = playerOptional.get();
            if(player.getLobbyID() == null) throw new IllegalStateException("Player is not part of any lobby!");
            Optional<Lobby> lobbyOptional = lobbyRepository.findById(player.getLobbyID());
            if(lobbyOptional.isPresent()) {
                Lobby lobby = lobbyOptional.get();
                lobby.removePlayer(playerUUID);
                lobbyRepository.save(lobby);
                playerRepository.delete(player);
                return LobbyMapper.toLobbyDTO(lobby);
            }
        }
        throw new IllegalStateException("The player doesn't exist!");
    }

    public LobbyDTO startLobby(String playerUUID) throws IllegalStateException {
        Optional<Player> playerOptional = playerRepository.findById(playerUUID);
        if(playerOptional.isEmpty()) throw new IllegalStateException("Player doesn't exist!");
        Optional<Lobby> lobbyOptional = lobbyRepository.findById(playerOptional.get().getLobbyID());
        if(lobbyOptional.isEmpty()) throw new IllegalStateException("The lobby doesn't exist!");

        //todo: add check for < 2 player

        Lobby lobby = lobbyOptional.get();
        lobby.setHasStarted(true);
        lobby.setHasDecision(false);
        lobby.setCards(null);

        lobbyRepository.save(lobby);
        return LobbyMapper.toLobbyDTO(lobby);
    }
}
