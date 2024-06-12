package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode
public class CareerCardDTO implements CardDTO {

    private String id;
    private String name;
    private int salary;
    private int bonus;
    private boolean needsDiploma;

    public CareerCardDTO(
                         @JsonProperty("name") String name,
                         @JsonProperty("salary") int salary,
                         @JsonProperty("bonus") int bonus,
                         @JsonProperty("needsDiploma") boolean needsDiploma) {

        this.name = name;
        this.salary = salary;
        this.bonus = bonus;
        this.needsDiploma = needsDiploma;
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public boolean needsDiploma() {
        return needsDiploma;
    }

    public void setNeedsDiploma(boolean needsDiploma) {
        this.needsDiploma = needsDiploma;
    }
}