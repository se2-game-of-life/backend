package se2.group3.backend.DTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se2.group3.backend.domain.cells.Cell;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class PlayerMoveRequest {

    private PlayerDTO playerDTO;

    private List<Cell> cells;

    private boolean pathChange;
}
