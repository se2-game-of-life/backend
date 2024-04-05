package se2.group3.backend.model.cards;

import se2.group3.backend.model.Card;
import se2.group3.backend.model.Player;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ActionCards")
public class ActionCard extends Card {
    private String description;
    private boolean affectOnePlayer;
    private boolean affectAllPlayers;
    private boolean affectBank;
    private int moneyAmount;

    public ActionCard(String name, String description, boolean affectOnePlayer, boolean affectAllPlayers, boolean affectBank, int moneyAmount) {
        super(name);
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

    @Override
    public void performAction(Player player) {
        // Implement action specific to action cards
    }

    @Override
    public String toString() {
        return "ActionCard{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                '}';
    }
}
