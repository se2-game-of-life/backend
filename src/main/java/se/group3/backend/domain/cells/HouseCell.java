package se.group3.backend.domain.cells;

import se.group3.backend.domain.player.Player;
import se.group3.backend.domain.game.Deck;

import java.util.List;

public class HouseCell extends Cell {
    public HouseCell(int number, String type, List<Integer> nextCells, int row, int col) {
        super(number, type, nextCells, row, col);
    }

    @Override
    public void performAction(Player player, Deck houseCardDeck) {
        drawAndPerformCardAction(player, houseCardDeck);
    }
}