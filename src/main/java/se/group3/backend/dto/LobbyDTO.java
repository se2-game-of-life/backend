package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


import java.util.List;

@Getter
public class LobbyDTO {

    private final long lobbyID;
    private final List<PlayerDTO> players;
    private final PlayerDTO currentPlayer;
    private final boolean hasDecision;
    private final List<CardDTO> cards;
    private final int spunNumber;
    private final boolean hasStarted;

    @JsonCreator
    public LobbyDTO(@JsonProperty("lobbyID") long lobbyID,
                    @JsonProperty("players") List<PlayerDTO> players,
                    @JsonProperty("currentPlayer") PlayerDTO currentPlayer,
                    @JsonProperty("hasDecision") boolean hasDecision,
                    @JsonProperty("cards") List<CardDTO> cards,
                    @JsonProperty("spunNumber") int spunNumber,
                    @JsonProperty("hasStarted") boolean hasStarted
    ) {
        this.lobbyID = lobbyID;
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.hasDecision = hasDecision;
        this.cards = cards;
        this.spunNumber = spunNumber;
        this.hasStarted = hasStarted;
    }

}
