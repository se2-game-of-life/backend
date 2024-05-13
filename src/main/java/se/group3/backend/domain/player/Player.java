package se.group3.backend.domain.player;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;

import java.util.ArrayList;
import java.util.List;

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
    private String playerUUID;
    private Long lobbyID;
    private String playerName;
    private int currentCellPosition;
    private int money;
    private CareerCard careerCard;
    private int numberOfPegs;
    private List<HouseCard> houses;
    private boolean collegeDegree;

    // Constructor
    public Player(String playerUUID, String playerName) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.currentCellPosition = 0;
        this.money = 250000; // Starting money
        this.numberOfPegs = 1;
        houses = new ArrayList<>();
        collegeDegree = false;
    }
}