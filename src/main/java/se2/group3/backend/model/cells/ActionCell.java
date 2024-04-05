package se2.group3.backend.model.cells;

import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Deck;
import se2.group3.backend.model.Player;
import se2.group3.backend.model.cards.ActionCard;

import java.util.List;


public class ActionCell extends Cell {

    public ActionCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }

    @Override
    public void performAction(Player player , Deck actionCardDeck) {
        drawAndPerformCardAction(player, actionCardDeck);
    }
}
