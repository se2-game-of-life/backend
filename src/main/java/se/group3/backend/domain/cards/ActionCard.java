package se.group3.backend.domain.cards;

import se.group3.backend.domain.game.Game;
import se.group3.backend.domain.player.Player;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "ActionCards")
public class ActionCard extends Card {
    private String description;
    private boolean affectOnePlayer;
    private boolean affectAllPlayers;
    private boolean affectBank;
    private int moneyAmount;
    private Game game;

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
        // Check the type of action and perform accordingly
        if (affectOnePlayer) {
            // Implement action affecting only one player
            // Example: Give money to the player
            player.setMoney(player.getMoney() + moneyAmount);
            System.out.println("You received " + moneyAmount + " money from action card: " + getName());
        }
    }

    @Override
    public String toString() {
        return "ActionCard{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                '}';
    }
}
