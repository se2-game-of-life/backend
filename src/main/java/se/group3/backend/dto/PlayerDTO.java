package se.group3.backend.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
                     @JsonProperty("careerCard") CareerCardDTO careerCard,
                     @JsonProperty("numberOfPegs") int numberOfPegs,
                     @JsonProperty("houses") List<HouseCardDTO> houses,
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
    private CareerCardDTO careerCard;
    private int numberOfPegs;
    private List<HouseCardDTO> houses;
    private boolean collegeDegree;
}