package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class CellDTO {
    protected String id;
    protected int number;
    private String type;
    protected List<Integer> nextCells;

    @JsonCreator
    public CellDTO(@JsonProperty("id") String id, @JsonProperty("number") int number, @JsonProperty("type") String type, @JsonProperty("nextCells")List<Integer> nextCells)  {
        this.id = id;
        this.number = number;
        this.type = type;
        this.nextCells = nextCells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellDTO cellDTO = (CellDTO) o;
        return number == cellDTO.number && Objects.equals(id, cellDTO.id) && Objects.equals(type, cellDTO.type) && Objects.equals(nextCells, cellDTO.nextCells);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, type, nextCells);
    }
}
