package se.group3.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.Cell;
import se.group3.backend.dto.BoardDTO;
import se.group3.backend.repositories.CellRepository;

import java.util.List;

@Slf4j
@Service
public class BoardService {

    private final CellRepository cellRepository;

    @Autowired
    public BoardService(CellRepository cellRepository) {
        this.cellRepository = cellRepository;
    }

    public BoardDTO fetchBoardData() {
        List<Cell> cells = cellRepository.findAll();
        // Construct BoardDTO from cells
        BoardDTO boardDTO= new BoardDTO(cells);
        log.debug(boardDTO.toString());
        return boardDTO;
    }
}
