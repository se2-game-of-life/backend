package se2.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class LobbyDTO {

    private long lobbyID;
    private PlayerDTO host;

    @JsonCreator
    public LobbyDTO(long lobbyID, PlayerDTO host) {
        this.lobbyID = lobbyID;
        this.host = host;
    }

    public long getLobbyID() {
        return this.lobbyID;
    }

    public PlayerDTO getHost() {
        return host;
    }
}
