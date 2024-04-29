package se.group3.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.group3.backend.domain.cells.Cell;

import java.util.List;

/**
 * Class to handle the payload from the frontend, and use it for the players actions
 */
@Getter
@Setter
@NoArgsConstructor
public class PlayerMoveRequest {

    private PlayerDTO playerDTO;

    private List<Cell> cells;

    private boolean pathChange;
}
