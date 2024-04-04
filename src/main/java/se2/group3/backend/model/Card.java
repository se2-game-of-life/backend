package se2.group3.backend.model;

public abstract class Card {
    private String name;

    public Card(String name) {
        this.name = name;
    }

    // Getters for name and description
    public String getName() {
        return name;
    }


    // Abstract method to perform the card's action
    public abstract void performAction(Player player);
}


