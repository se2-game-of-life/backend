package se2.group3.backend.dto;

import lombok.Getter;

@Getter
public class JoinSessionRequest {

    private final long sessionID;
    private final String playerName;

    public JoinSessionRequest(long sessionID, String playerName) {
        this.sessionID = sessionID;
        this.playerName = playerName;
    }
}
