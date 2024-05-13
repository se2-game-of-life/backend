package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameStateUpdate {

    private final LobbyDTO lobbyDTO;

    @JsonCreator
    public GameStateUpdate(@JsonProperty("lobbyDTO") LobbyDTO lobbyDTO) {
        this.lobbyDTO = lobbyDTO;
    }

}
