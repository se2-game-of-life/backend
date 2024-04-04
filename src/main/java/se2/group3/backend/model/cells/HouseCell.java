package se2.group3.backend.model.cells;

import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Deck;
import se2.group3.backend.model.Player;
import se2.group3.backend.model.cards.HouseCard;

public class HouseCell extends Cell {
    private Deck<HouseCard> houseCardDeck;

    public HouseCell(int position, Deck<HouseCard> houseCardDeck) {
        super(position);
        this.houseCardDeck = houseCardDeck;
    }

    @Override
    public void performAction(Player player) {
        drawAndPerformCardAction(player, houseCardDeck);
    }
}