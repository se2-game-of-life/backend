package se2.group3.backend.dto;

import lombok.Getter;

@Getter
public class JoinLobbyRequest {

    private final long lobbyID;
    private final String playerName;

    public JoinLobbyRequest(long lobbyID, String playerName) {
        this.lobbyID = lobbyID;
        this.playerName = playerName;
    }
}
