package se2.group3.backend.dto.mapper;

import se2.group3.backend.dto.LobbyDTO;
import se2.group3.backend.model.Lobby;

public class LobbyMapper {
    public static LobbyDTO toLobbyDTO(Lobby newLobby) {
        return new LobbyDTO(newLobby.getId(), newLobby.getHost());
    }
}
