package se.group3.backend.dto.mapper;

import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.domain.lobby.Lobby;
import se.group3.backend.dto.PlayerDTO;

import java.util.Arrays;
import java.util.Objects;

public class LobbyMapper {

    private LobbyMapper() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    public static LobbyDTO toLobbyDTO(Lobby newLobby) {
        return new LobbyDTO(
                newLobby.getId(),
                PlayerMapper.mapPlayerToDTO(newLobby.getHost()),
                Arrays.stream(newLobby.getPlayers()).filter(Objects::nonNull).map(PlayerMapper::mapPlayerToDTO).toArray(PlayerDTO[]::new));
    }
}
