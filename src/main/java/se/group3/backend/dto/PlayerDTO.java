package se.group3.backend.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlayerDTO {

    @JsonCreator
    public PlayerDTO(@JsonProperty("playerUUID") String playerUUID,
                     @JsonProperty("playerName") String playerName,
                     @JsonProperty("lobbyID") Long lobbyID,
                     @JsonProperty("currentCellPosition") int currentCellPosition,
                     @JsonProperty("money") int money,
                     @JsonProperty("careerCard") CareerCard careerCard,
                     @JsonProperty("numberOfPegs") int numberOfPegs,
                     @JsonProperty("houses") List<HouseCard> houses,
                     @JsonProperty("collageDegree") boolean collegeDegree
    ) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.lobbyID = lobbyID;
        this.currentCellPosition = currentCellPosition;
        this.money = money;
        this.careerCard = careerCard;
        this.numberOfPegs = numberOfPegs;
        this.houses = houses;
        this.collegeDegree = collegeDegree;
    }

    private String playerUUID;
    private Long lobbyID;
    private String playerName;
    private int currentCellPosition;
    private int money;
    private CareerCard careerCard;
    private int numberOfPegs;
    private List<HouseCard> houses;
    private boolean collegeDegree;
}