package se2.group3.backend.domain.cards;

import se2.group3.backend.domain.player.Player;
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
    public void performAction(Player player) {
        // Implement action specific to career cards
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

}

