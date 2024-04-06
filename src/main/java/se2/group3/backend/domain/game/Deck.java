package se2.group3.backend.domain.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck<T> {
    private List<T> cards;

    public Deck() {
        this.cards = new ArrayList<>();
    }
    public Deck(List<T> initialCards) { this.cards = initialCards; }

    // Method to add a card to the deck
    public void addCard(T card) {
        cards.add(card);
    }

    // Method to shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public T drawCard() {
        if (cards.isEmpty()) {
            // Handle empty deck scenario (throw an exception, return null, etc.)
            // For now, let's assume it returns null
            return null;
        }
        T drawnCard = cards.remove(0); // Remove and return the top card
        return drawnCard;
    }

    public void returnCardToBottom(T card) {
        cards.add(card); // Add the card to the bottom of the deck
    }

}
