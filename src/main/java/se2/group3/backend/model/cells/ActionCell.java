package se2.group3.backend.model.cells;

import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Deck;
import se2.group3.backend.model.Player;
import se2.group3.backend.model.cards.ActionCard;


public class ActionCell extends Cell {
    private Deck<ActionCard> actionCardDeck;

    public ActionCell(int position, Deck<ActionCard> actionCardDeck) {
        super(position);
        this.actionCardDeck = actionCardDeck;
    }

    @Override
    public void performAction(Player player) {
        drawAndPerformCardAction(player, actionCardDeck);
    }
}
