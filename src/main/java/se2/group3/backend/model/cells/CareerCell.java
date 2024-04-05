package se2.group3.backend.model.cells;


import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Deck;
import se2.group3.backend.model.Player;
import se2.group3.backend.model.cards.CareerCard;

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