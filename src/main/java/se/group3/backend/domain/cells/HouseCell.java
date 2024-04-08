package se.group3.backend.domain.cells;

import se.group3.backend.domain.player.Player;
import se.group3.backend.domain.game.Deck;

import java.util.List;

public class HouseCell extends Cell {
    public HouseCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }
    @Override
    public void performAction(Player player, Deck houseCardDeck) {
        drawAndPerformCardAction(player, houseCardDeck);
    }
}