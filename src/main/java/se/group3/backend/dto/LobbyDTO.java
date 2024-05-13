package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import se.group3.backend.domain.Player;
import se.group3.backend.domain.cards.Card;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LobbyDTO {

    private final long lobbyID;
    private final List<PlayerDTO> players;
    private final Player currentPlayer;
    private final boolean hasDecision;
    private final List<Card> cards;
    private final int spunNumber;

    @JsonCreator
    public LobbyDTO(@JsonProperty("lobbyID") long lobbyID,
                    @JsonProperty("players") List<PlayerDTO> players,
                    @JsonProperty("currentPlayer") Player currentPlayer,
                    @JsonProperty("hasDecision") boolean hasDecision,
                    @JsonProperty("cards") List<Card> cards,
                    @JsonProperty("spunNumber") int spunNumber
    ) {
        this.lobbyID = lobbyID;
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.hasDecision = hasDecision;
        this.cards = cards;
        this.spunNumber = spunNumber;
    }

}
