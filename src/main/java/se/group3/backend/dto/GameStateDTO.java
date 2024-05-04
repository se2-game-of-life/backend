package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import se.group3.backend.domain.cards.Card;
import se.group3.backend.domain.player.Player;

import java.util.List;

@Getter
@Setter
public class GameStateDTO {

    /**
     * UUID from player which has to take the turn
     */
    private PlayerDTO nextPlayer;

    private List<PlayerDTO> players;

    private int diceResult;

    private List<Card> cardOptions;

    @JsonCreator
    public GameStateDTO(@JsonProperty("playerTurn") PlayerDTO nextPlayer, @JsonProperty("players") List<PlayerDTO> players, @JsonProperty("diceResult") int diceResult, @JsonProperty("cardOptions") List<Card> cardOptions) {
        this.nextPlayer = nextPlayer;
        this.players = players;
        this.diceResult = diceResult;
        this.cardOptions = cardOptions;
    }
}
