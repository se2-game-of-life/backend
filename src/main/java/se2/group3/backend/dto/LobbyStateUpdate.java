package se2.group3.backend.dto;

import lombok.Getter;
import se2.group3.backend.model.Lobby;

@Getter
public class LobbyStateUpdate {
    private final long lobbyID;

    public LobbyStateUpdate(Lobby lobby) {
        this.lobbyID = lobby.getId();
    }
}
