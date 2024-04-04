package se2.group3.backend.model.cards;

import se2.group3.backend.model.Card;
import se2.group3.backend.model.Player;

public class ActionCard extends Card {
    private String description;
    public ActionCard(String name, String description) {
        super(name);
        this.description = description;
        //TODO: extend ActionCard attributes as needed
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void performAction(Player player) {
        // Implement action specific to action cards
    }
}