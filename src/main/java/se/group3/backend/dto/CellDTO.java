package se.group3.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CellDTO {

    private final String id;
    private final int number;
    private final String type;
    private final List<Integer> nextCells;
    private final int row;
    private final int col;

    @JsonCreator
    public CellDTO(@JsonProperty("id") String id,
                   @JsonProperty("number") int number,
                   @JsonProperty("type") String type,
                   @JsonProperty("nextCells")List<Integer> nextCells,
                   @JsonProperty("row") int row,
                   @JsonProperty("col") int col)  {
        this.id = id;
        this.number = number;
        this.type = type;
        this.nextCells = nextCells;
        this.row = row;
        this.col = col;
    }
}
