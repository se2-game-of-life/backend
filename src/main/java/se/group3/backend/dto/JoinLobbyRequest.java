package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class JoinLobbyRequest {

    private long lobbyID;
    private String playerName;

    @JsonCreator
    public JoinLobbyRequest(@JsonProperty("lobbyID") long lobbyID, @JsonProperty("playerName") String playerName) {
        this.lobbyID = lobbyID;
        this.playerName = playerName;
    }
}
