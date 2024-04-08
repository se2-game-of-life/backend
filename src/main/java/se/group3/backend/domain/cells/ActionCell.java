package se.group3.backend.domain.cells;

import se.group3.backend.domain.game.Deck;
import se.group3.backend.domain.player.Player;

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
