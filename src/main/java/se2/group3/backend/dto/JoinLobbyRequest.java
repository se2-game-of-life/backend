package se2.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class JoinLobbyRequest {

    private long lobbyID;
    private PlayerDTO player;

    @JsonCreator
    public JoinLobbyRequest(@JsonProperty("lobbyID") long lobbyID, @JsonProperty("player") PlayerDTO player) {
        this.lobbyID = lobbyID;
        this.player = player;
    }
}
