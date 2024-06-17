package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode
public class ActionCardDTO implements CardDTO {

    private String id;

    private String name;

    private String description;

    private boolean affectOnePlayer;

    private boolean affectAllPlayers;

    private boolean affectBank;

    private int moneyAmount;

    public ActionCardDTO(
                         @JsonProperty("name") String name,
                         @JsonProperty("description") String description,
                         @JsonProperty("affectOnePlayer") boolean affectOnePlayer,
                         @JsonProperty("affectAllPlayers") boolean affectAllPlayers,
                         @JsonProperty("affectBank") boolean affectBank,
                         @JsonProperty("moneyAmount") int moneyAmount) {
        this.name = name;
        this.description = description;
        this.affectOnePlayer = affectOnePlayer;
        this.affectAllPlayers = affectAllPlayers;
        this.affectBank = affectBank;
        this.moneyAmount = moneyAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public boolean isAffectOnePlayer() {
        return affectOnePlayer;
    }


    public boolean isAffectAllPlayers() {
        return affectAllPlayers;
    }


    public boolean isAffectBank() {
        return affectBank;
    }


    public int getMoneyAmount() {
        return moneyAmount;
    }

}