package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LobbyDTO {

    private final long lobbyID;
    private final List<PlayerDTO> players;

    @JsonCreator
    public LobbyDTO(@JsonProperty("lobbyID") long lobbyID, @JsonProperty("players") List<PlayerDTO> players) {
        this.lobbyID = lobbyID;
        this.players = players;
    }

    public long getLobbyID() {
        return this.lobbyID;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }
}
