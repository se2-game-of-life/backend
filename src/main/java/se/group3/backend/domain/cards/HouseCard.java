package se.group3.backend.domain.cards;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "HouseCards")
public class HouseCard implements Card {

    private String id;
    private final String name;
    private final int purchasePrice;
    private final int redSellPrice;
    private final int blackSellPrice;


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


}
