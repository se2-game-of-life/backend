package se2.group3.backend.dto.mapper;

import se2.group3.backend.dto.LobbyDTO;
import se2.group3.backend.domain.lobby.Lobby;

public class LobbyMapper {
    private LobbyMapper() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }
    public static LobbyDTO toLobbyDTO(Lobby newLobby) {
        return new LobbyDTO(newLobby.getId(), PlayerMapper.toPlayerDTO(newLobby.getHost()));
    }
}
