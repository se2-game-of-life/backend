package se2.group3.backend.domain.player;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se2.group3.backend.domain.cards.CareerCard;

/**
 * Player Class from the Database
 */

@Document(collection = "player")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Player {

    @Id
    private String playerID;

    private String playerName;
    private int currentCellPosition;
    private int money;

    private CareerCard careerCard;

    //Booleans for the different paths the game offers
    private boolean isCollegePath;
    private boolean isMarriedPath;
    private boolean isGrowFamiliePath;
    private boolean hasMidlifeCrisis;
    private boolean isRetireEarlyPath;

    private int investmentNumber; // The chosen investment number
    private int investmentLevel; // The current level of investment
    private int numberOfPegs;

    // Constructor
    public Player(String playerName) {
        this.playerName = playerName;
        this.currentCellPosition = 0;
        this.money = 250000; // Starting money
        this.isCollegePath = false;
        this.investmentNumber =0;
        this.investmentLevel = 0;
        this.numberOfPegs = 1;
    }
}