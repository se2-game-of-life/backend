package se2.group3.backend.model.cards;

import se2.group3.backend.model.Card;
import se2.group3.backend.model.Player;

public class HouseCard extends Card {
    public HouseCard(String name) {
        super(name);
    }

    @Override
    public void performAction(Player player) {
        // Implement action specific to house cards
    }
}
