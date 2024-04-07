package se2.group3.backend.controllers.player;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import se2.group3.backend.DTOs.PlayerDTO;
import se2.group3.backend.DTOs.PlayerMoveRequest;
import se2.group3.backend.domain.cells.Cell;
import se2.group3.backend.domain.cells.StopCell;
import se2.group3.backend.services.player.PlayerService;

import java.util.List;

/**
 * Controller to handle player actions during the game
 */
@Slf4j
@Controller("/player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * this method will be called when a user does his first move
     * @param moveRequest payload from frontend
     */
    @MessageMapping("/start-game")
    public void startGameActions(@Payload PlayerMoveRequest moveRequest) {
        PlayerDTO dto = moveRequest.getPlayerDTO();
        playerService.chooseCollagePath(dto);
        //todo: all actions a player does when the game starts
    }

    /**
     * handles the fields the player wants to move
     * @param moveRequest payload from frontend
     */
    @MessageMapping("/move")
    public void movePlayer(@Payload PlayerMoveRequest moveRequest) {
        PlayerDTO dto = moveRequest.getPlayerDTO();
        List<Cell> cells = moveRequest.getCells();
        for(Cell cell : cells) {
            if(cell instanceof StopCell) {
                playerService.checkCellAndPerformAction(dto, cell);
                break;
            }
            //perform normal action
        }
    }

    /**
     * handles a players spin
     * @param moveRequest payload from frontend
     */
    @MessageMapping("/spin")
    public void spin(@Payload PlayerMoveRequest moveRequest) {
        playerService.spin(moveRequest.getPlayerDTO());
    }

    /**
     * handles the college path change
     * @param moveRequest payload from frontend
     */
    @MessageMapping("/college-path")
    public void collegePath(@Payload PlayerMoveRequest moveRequest) {
        PlayerDTO dto = moveRequest.getPlayerDTO();
        boolean pathChange = moveRequest.isPathChange();
        dto.setCollegePath(pathChange);
        playerService.chooseCollagePath(dto);
    }

    /**
     * handles the marry path change
     * @param moveRequest payload from frontend
     */
    @MessageMapping("/marry-path")
    public void marryPath(@Payload PlayerMoveRequest moveRequest) {
        PlayerDTO dto = moveRequest.getPlayerDTO();
        boolean pathChange = moveRequest.isPathChange();
        dto.setMarriedPath(pathChange);
        playerService.chooseMarryPath(dto);
    }

    /**
     * handles the grow-family path change
     * @param moveRequest payload from frontend
     */
    @MessageMapping("/grow-family-path")
    public void growFamilyPath(@Payload PlayerMoveRequest moveRequest) {
        PlayerDTO dto = moveRequest.getPlayerDTO();
        boolean pathChange = moveRequest.isPathChange();
        dto.setGrowFamiliePath(pathChange);
        playerService.chooseGrowFamilyPath(dto);
    }

    /**
     * handles the midlifecrisis path change
     * @param moveRequest payload from frontend
     */
    @MessageMapping("/midlifecrisis")
    public void hasMidlifeCrisisPath(@Payload PlayerMoveRequest moveRequest) {
        PlayerDTO dto = moveRequest.getPlayerDTO();
        boolean pathChange = moveRequest.isPathChange();
        dto.setHasMidlifeCrisis(pathChange);
        playerService.midLifeCrisisPath(dto);
    }

    /**
     * handles the retire path change
     * @param moveRequest payload from frontend
     */
    @MessageMapping("/retire-path")
    public void retirePath(@Payload PlayerMoveRequest moveRequest) {
        PlayerDTO dto = moveRequest.getPlayerDTO();
        boolean pathChange = moveRequest.isPathChange();
        dto.setRetireEarlyPath(pathChange);
        playerService.chooseRetireEarlyPath(dto);
    }
}
