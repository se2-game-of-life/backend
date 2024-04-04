package se2.group3.backend.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck<T> {
    private List<T> cards;

    // Constructor to initialize an empty deck
    public Deck() {
        cards = new ArrayList<>();
    }

    // Method to add a card to the deck
    public void addCard(T card) {
        cards.add(card);
    }

    // Method to shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public T drawCard() {
        T drawnCard = cards.remove(0); // Remove and return the top card
        //TODO: some cards will actually be removed from deck (House if bought, Career if chosen)
        cards.add(drawnCard); // Add the drawn card to the bottom of the deck
        return drawnCard;
    }

}
