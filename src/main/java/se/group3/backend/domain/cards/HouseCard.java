package se.group3.backend.domain.cards;

import se.group3.backend.domain.game.Deck;
import se.group3.backend.domain.player.Player;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "HouseCards")
public class HouseCard extends Card {
    private int purchasePrice;
    private int redSellPrice;
    private int blackSellPrice;

    public HouseCard(String name, int purchasePrice, int redSellPrice, int blackSellPrice) {
        super(name);
        this.purchasePrice = purchasePrice;
        this.redSellPrice = redSellPrice;
        this.blackSellPrice = blackSellPrice;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public int getRedSellPrice() {
        return redSellPrice;
    }

    public int getBlackSellPrice() {
        return blackSellPrice;
    }

    @Override
    public void performAction(Player player) {
        if (player.getMoney() >= purchasePrice) {
            player.setMoney(player.getMoney() - purchasePrice);
            player.assignHouseCard(this);
            System.out.println("Congratulations! You have bought the house: " + getName());
        } else {
            System.out.println("Sorry, you don't have enough money to buy this house.");
        }
    }

    public void sellHouse(Player player, boolean isRedOnWheel) {
        int sellPrice = isRedOnWheel ? redSellPrice : blackSellPrice;

        if (player.getOwnedHouses().contains(this)) {
            player.getOwnedHouses().remove(this);
            player.setMoney(player.getMoney() + sellPrice);
            System.out.println("You have sold the house: " + getName() + " for " + sellPrice);
        } else {
            System.out.println("You don't own this house.");
        }
    }

    @Override
    public String toString() {
        return "HouseCard{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                '}';
    }
}

