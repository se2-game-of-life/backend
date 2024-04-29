package se.group3.backend.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import se.group3.backend.domain.cards.Card;
import se.group3.backend.domain.player.Player;

import java.util.List;

@Getter
@Setter
@JsonSerialize
public class GameStateDTO {

    /**
     * UUID from player which has to take the turn
     */
    private String playerTurn;

    private List<Player> players;

    private int diceResult;

    private List<Card> cardOptions;

}
