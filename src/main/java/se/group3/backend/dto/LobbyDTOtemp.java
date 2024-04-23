package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//review: we have two different PlayerDTO classes, we need to refactor it to one

//!!copied LobbyDTO.java class from Thomas Schleicher, but using another PlayerDTO class!!
public class LobbyDTOtemp {

    private final long lobbyID;
    private final se.group3.backend.DTOs.PlayerDTO host;
    private final se.group3.backend.DTOs.PlayerDTO[] players;

    @JsonCreator
    public LobbyDTOtemp(@JsonProperty("lobbyID") long lobbyID, @JsonProperty("host") se.group3.backend.DTOs.PlayerDTO host, @JsonProperty("players") se.group3.backend.DTOs.PlayerDTO[] players) {
        this.lobbyID = lobbyID;
        this.host = host;
        this.players = players;
    }

    public long getLobbyID() {
        return this.lobbyID;
    }

    public se.group3.backend.DTOs.PlayerDTO getHost() {
        return host;
    }

    public se.group3.backend.DTOs.PlayerDTO[] getPlayers() {
        return players;
    }
}
