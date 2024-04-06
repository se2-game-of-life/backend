package se2.group3.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se2.group3.backend.domain.cells.Cell;
import se2.group3.backend.domain.cells.*;
import se2.group3.backend.repositories.CellRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CellService {

    private final CellRepository cellRepository;

    @Autowired
    public CellService(CellRepository cellRepository) {
        this.cellRepository = cellRepository;
    }

    public List<Cell> getAllCells() {
        List<Cell> cells = cellRepository.findAll();
        return castCellsByType(cells);
    }


    private List<Cell> castCellsByType(List<Cell> cells) {

        List<Cell> castedCells = new ArrayList<>();

        for (Cell cell : cells) {
            if ("action".equals(cell.getType())) {
                castedCells.add(new ActionCell(cell.getPosition(), cell.getNextCells()));
            }
            else if ("addPeg".equals(cell.getType())) {
                castedCells.add(new AddPegCell(cell.getPosition(), cell.getNextCells()));
            }
            else if ("career".equals(cell.getType())) {
                castedCells.add(new CareerCell(cell.getPosition(), cell.getNextCells()));
            }
            else if ("finalRetirementCell".equals(cell.getType())) {
                castedCells.add(new FinalRetirementCell(cell.getPosition(), cell.getNextCells()));
            }
            else if ("getMarriedStopCell".equals(cell.getType())) {
                castedCells.add(new GetMarriedStopCell(cell.getPosition(), cell.getNextCells()));
            }
            else if ("houseCell".equals(cell.getType())) {
                castedCells.add(new HouseCell(cell.getPosition(), cell.getNextCells()));
            }
            else if ("investCell".equals(cell.getType())) {
                castedCells.add(new InvestCell(cell.getPosition(), cell.getNextCells()));
            }
            else if ("paydayCell".equals(cell.getType())) {
                castedCells.add(new PaydayCell(cell.getPosition(), cell.getNextCells()));
            }
            else if ("spinToGraduateStopCell".equals(cell.getType())) {
                castedCells.add(new SpinToGraduateStopCell(cell.getPosition(), cell.getNextCells()));
            }
            else{
                castedCells.add(cell);
            }
        }
        return castedCells;
    }
}
