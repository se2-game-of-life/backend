package se2.group3.backend.model.cards;

import se2.group3.backend.model.Card;
import se2.group3.backend.model.Player;
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
        // Implement action specific to house cards
    }

    @Override
    public String toString() {
        return "HouseCard{" +
                "name='" + getName() + '\'' +
                '}';
    }
}
