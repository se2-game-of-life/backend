package se.group3.backend.domain.cards;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CareerCards")
public class CareerCard implements Card {
    private String id;
    private final String name;
    private final int salary;
    private final int bonus;
    private final boolean needsDiploma;

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



}

