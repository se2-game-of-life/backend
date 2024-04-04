package se2.group3.backend.model.cells;


import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Deck;
import se2.group3.backend.model.Player;
import se2.group3.backend.model.cards.CareerCard;

public class CareerCell extends Cell {
    private Deck<CareerCard> careerCardDeck;

    public CareerCell(int position, Deck<CareerCard> careerCardDeck) {
        super(position);
        this.careerCardDeck = careerCardDeck;
    }

    @Override
    public void performAction(Player player) {
        drawAndPerformCardAction(player, careerCardDeck);
    }
}