package se2.group3.backend.controllers.player;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import se2.group3.backend.DTOs.PlayerDTO;
import se2.group3.backend.DTOs.PlayerMoveRequest;
import se2.group3.backend.domain.cells.Cell;
import se2.group3.backend.domain.cells.StopCell;
import se2.group3.backend.services.player.PlayerService;

import java.util.List;

@Slf4j
@Controller("/player")
public class PlayerController {

    private final PlayerService service;

    @Autowired
    public PlayerController(@Qualifier("playerService") PlayerService service) {
        this.service = service;
    }


    @MessageMapping("/start-game")
    public void startGameActions(@Payload PlayerMoveRequest moveRequest) {
        PlayerDTO dto = moveRequest.getPlayerDTO();
        service.chooseCollagePath(dto);
        //todo: all actions a player does when the game starts
    }

    @MessageMapping("/move")
    public void movePlayer(@Payload PlayerMoveRequest moveRequest) {
        PlayerDTO dto = moveRequest.getPlayerDTO();
        List<Cell> cells = moveRequest.getCells();
        for(Cell cell : cells) {
            if(cell instanceof StopCell) {
                service.checkCellAndPerformAction(dto, cell);
                break;
            }
            //perform normal action
        }
    }


    @MessageMapping("/spin")
    public void spin(PlayerDTO dto) {
        service.spin(dto);
    }

    @MessageMapping("/college-path")
    public void collegePath(@Payload PlayerMoveRequest request) {
        PlayerDTO dto = request.getPlayerDTO();
        boolean pathChange = request.isPathChange();
        dto.setCollegePath(pathChange);
        service.chooseCollagePath(dto);
    }

    @MessageMapping("/marry-path")
    public void marryPath(@Payload PlayerMoveRequest request) {
        PlayerDTO dto = request.getPlayerDTO();
        boolean pathChange = request.isPathChange();
        dto.setMarriedPath(pathChange);
        service.chooseMarryPath(dto);
    }

    @MessageMapping("/grow-family-path")
    public void growFamilyPath(@Payload PlayerMoveRequest request) {
        PlayerDTO dto = request.getPlayerDTO();
        boolean pathChange = request.isPathChange();
        dto.setGrowFamiliePath(pathChange);
        service.chooseGrowFamilyPath(dto);
    }

    @MessageMapping("/college-path")
    public void hasMidlifeCrisisPath(@Payload PlayerMoveRequest request) {
        PlayerDTO dto = request.getPlayerDTO();
        boolean pathChange = request.isPathChange();
        dto.setHasMidlifeCrisis(pathChange);
        service.midLifeCrisisPath(dto);
    }

    @MessageMapping("/college-path")
    public void retirePath(@Payload PlayerMoveRequest request) {
        PlayerDTO dto = request.getPlayerDTO();
        boolean pathChange = request.isPathChange();
        dto.setRetireEarlyPath(pathChange);
        service.chooseRetireEarlyPath(dto);
    }
}
