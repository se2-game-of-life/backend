package se2.group3.backend.domain.cards;
import org.springframework.data.annotation.Id;
import se2.group3.backend.domain.player.Player;


public abstract class Card {
    @Id
    protected String id;
    private final String name;

    protected Card(String name) {
        this.name = name;
    }

    // Getters for name and description
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
    // Abstract method to perform the card's action
    public abstract void performAction(Player player);
}


