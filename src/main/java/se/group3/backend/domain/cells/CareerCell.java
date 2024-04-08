package se.group3.backend.domain.cells;


import se.group3.backend.domain.game.Deck;
import se.group3.backend.domain.player.Player;

import java.util.List;

public class CareerCell extends Cell {
    public CareerCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }

    @Override
    public void performAction(Player player, Deck careerCardDeck) {
        drawAndPerformCardAction(player, careerCardDeck);
    }
}