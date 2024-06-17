package se.group3.backend.domain.cards;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ActionCards")
public class ActionCard implements Card {
    @Id
    private String id;
    private String name;
    private final String description;
    private final boolean affectOnePlayer;
    private final boolean affectAllPlayers;
    private final boolean affectBank;
    private final int moneyAmount;

    public ActionCard(String id, String name, String description, boolean affectOnePlayer, boolean affectAllPlayers, boolean affectBank, int moneyAmount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.affectOnePlayer = affectOnePlayer;
        this.affectAllPlayers = affectAllPlayers;
        this.affectBank = affectBank;
        this.moneyAmount = moneyAmount;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


}
