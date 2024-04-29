package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.group3.backend.domain.cards.CareerCard;

@Getter
@Setter
@NoArgsConstructor
public class PlayerDTO {

    @JsonCreator
    public PlayerDTO(@JsonProperty("playerName") String playerName) {
        this.playerName = playerName;
    }


    private String playerID;
    private String playerName;
    private int currentCellPosition;
    private int money;

    private CareerCard careerCard;

    // Booleans for the different paths the game offers
    private boolean isCollegePath;
    private boolean isMarriedPath;
    private boolean isGrowFamilyPath;
    private boolean hasMidlifeCrisis;
    private boolean isRetireEarlyPath;

    private int investmentNumber; // The chosen investment number
    private int investmentLevel; // The current level of investment-
    private int numberOfPegs;


}
