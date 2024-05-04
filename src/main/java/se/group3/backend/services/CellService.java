package se.group3.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.cells.*;
import se.group3.backend.repositories.CellRepository;
import se.group3.backend.domain.cells.Cell;

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
            if ("actionCell".equals(cell.getType())) {
                castedCells.add(new ActionCell(
                        cell.getNumber(),
                        cell.getType(),
                        cell.getNextCells(),
                        cell.getRow(),
                        cell.getCol()
                ));
            } else if ("addPegCell".equals(cell.getType())) {
                castedCells.add(new AddPegCell(
                        cell.getNumber(),
                        cell.getType(),
                        cell.getNextCells(),
                        cell.getRow(),
                        cell.getCol()
                ));
            } else if ("careerCell".equals(cell.getType())) {
                castedCells.add(new CareerCell(
                        cell.getNumber(),
                        cell.getType(),
                        cell.getNextCells(),
                        cell.getRow(),
                        cell.getCol()
                ));
            } else if ("finalRetirementCell".equals(cell.getType())) {
                castedCells.add(new FinalRetirementCell(
                        cell.getNumber(),
                        cell.getType(),
                        cell.getNextCells(),
                        cell.getRow(),
                        cell.getCol()
                ));
            } else if ("getMarriedStopCell".equals(cell.getType())) {
                castedCells.add(new GetMarriedStopCell(
                        cell.getNumber(),
                        cell.getType(),
                        cell.getNextCells(),
                        cell.getRow(),
                        cell.getCol()
                ));
            } else if ("houseCell".equals(cell.getType())) {
                castedCells.add(new HouseCell(
                        cell.getNumber(),
                        cell.getType(),
                        cell.getNextCells(),
                        cell.getRow(),
                        cell.getCol()
                ));
            } else if ("investCell".equals(cell.getType())) {
                castedCells.add(new InvestCell(
                        cell.getNumber(),
                        cell.getType(),
                        cell.getNextCells(),
                        cell.getRow(),
                        cell.getCol()
                ));
            } else if ("paydayCell".equals(cell.getType())) {
                castedCells.add(new PaydayCell(
                        cell.getNumber(),
                        cell.getType(),
                        cell.getNextCells(),
                        cell.getRow(),
                        cell.getCol()
                ));
            } else if ("spinToGraduateStopCell".equals(cell.getType())) {
                castedCells.add(new SpinToGraduateStopCell(
                        cell.getNumber(),
                        cell.getType(),
                        cell.getNextCells(),
                        cell.getRow(),
                        cell.getCol()
                ));
            } else {
                castedCells.add(cell);
            }
        }
        return castedCells;
    }

}
