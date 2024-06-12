package se.group3.backend.domain.cards;

import se.group3.backend.domain.Player;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CareerCards")
public class CareerCard implements Card {
    private String id;
    private String name;
    private int salary;
    private int bonus;
    private boolean needsDiploma;

    public CareerCard(String id, String name, int salary, int bonus, boolean needsDiploma) {
     this.id = id;
        this.name = name;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*    @Override
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
    }*/

}

