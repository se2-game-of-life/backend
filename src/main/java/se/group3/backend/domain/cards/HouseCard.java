package se.group3.backend.domain.cards;

import se.group3.backend.domain.Player;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "HouseCards")
public class HouseCard implements Card {

    private String id;
    private String name;
    private int purchasePrice;
    private int redSellPrice;
    private int blackSellPrice;


    public HouseCard(String id, String name, int purchasePrice, int redSellPrice, int blackSellPrice) {
        this.id = id;
        this.name = name;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /*    @Override
    public void performAction(Player player) {
        // Implement action specific to house cards
    }

    @Override
    public String toString() {
        return "HouseCard{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                '}';
    }*/
}
