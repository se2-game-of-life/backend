package se2.group3.backend.model;


public abstract class Card {
    private String name;
    private String description;

    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters for name and description
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    // Abstract method to perform the card's action
    public abstract void performAction(Player player);
}


