package se2.group3.backend.model.cards;

import se2.group3.backend.model.Card;
import se2.group3.backend.model.Player;

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

}

