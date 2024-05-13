package se.group3.backend.domain.cards;

import se.group3.backend.domain.game.Deck;
import se.group3.backend.domain.player.Player;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CareerCards")
public class CareerCard extends Card {
    private int salary;
    private int bonus;
    private boolean needsDiploma;

    public CareerCard(String name, int salary, int bonus, boolean needsDiploma) {
        super(name);
        this.salary = salary;
        this.bonus = bonus;
        this.needsDiploma = needsDiploma;
    }

    // Getters for salary and needsDiploma

    public int getSalary() {
        return salary;
    }

    public int getBonus() {
        return bonus;
    }

    public boolean needsDiploma() {
        return needsDiploma;
    }

    @Override
    public void performAction(Player player, String buttonClicked) {
        // Get the career card deck
        Deck<CareerCard> deck = getCareerCardDeck();

        // Draw two career cards
        CareerCard drawnCard1 = deck.drawCard();
        CareerCard drawnCard2 = deck.drawCard();

        // If the deck is empty, handle it according to your game rules
        if (drawnCard1 == null || drawnCard2 == null) {
            System.out.println("No more career cards available.");
            return;
        }

        CareerCard chosenCard;
        CareerCard unchosenCard;

        if (buttonClicked.equals("Button One Clicked")) {
            chosenCard = drawnCard1;
            unchosenCard = drawnCard2;
        } else if (buttonClicked.equals("Button Two Clicked")) {
            chosenCard = drawnCard2;
            unchosenCard = drawnCard1;
        } else {
            // Handle unexpected button clicks here
            return;
        }

        // Give the chosen card to the player
        player.assignCareerCard(chosenCard); // Assuming you have a method in Player class to assign career cards

        // Add the unchosen card back to the deck
        deck.addCard(drawnCard2);
    }


    // Override the toString() method to print the contents of the CareerCard
    @Override
    public String toString() {
        return "CareerCard{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", needsDiploma=" + needsDiploma +
                '}';
    }
    // Example method to get the career card deck
    private Deck<CareerCard> getCareerCardDeck() {
        // Implement logic to get the career card deck
        return null;
    }

}

