package se2.group3.backend.model.cards;

import se2.group3.backend.model.Card;
import se2.group3.backend.model.Player;

public class ActionCard extends Card {
    public ActionCard(String name, String description) {
        super(name, description);
        //TODO: extend ActionCard attributes as needed
    }

    @Override
    public void performAction(Player player) {
        // Implement action specific to action cards
    }
}