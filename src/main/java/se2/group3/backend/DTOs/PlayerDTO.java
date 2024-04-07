package se2.group3.backend.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se2.group3.backend.domain.cards.CareerCard;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonSerialize
public class PlayerDTO {


    private String playerID;
    private String playerName;
    private int currentCellPosition;
    private int money;

    private CareerCard careerCard;

    // Booleans for the different paths the game offers
    private boolean isCollegePath;
    private boolean isMarriedPath;
    private boolean isGrowFamiliePath;
    private boolean hasMidlifeCrisis;
    private boolean isRetireEarlyPath;

    private int investmentNumber; // The chosen investment number
    private int investmentLevel; // The current level of investment-
    private int numberOfPegs;
}