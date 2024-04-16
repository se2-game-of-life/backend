package se.group3.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.group3.backend.DTOs.PlayerDTO;
import se.group3.backend.domain.game.Game;
import se.group3.backend.domain.lobby.Lobby;
import se.group3.backend.dto.CellDTO;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.repositories.ActionCardRepository;
import se.group3.backend.repositories.CareerCardRepository;
import se.group3.backend.repositories.HouseCardRepository;


/**
 * Implementation of the Interface GameService
 */
@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private Game game;

    private CareerCardRepository careerCardRepository;
    private ActionCardRepository actionCardRepository;
    private HouseCardRepository houseCardRepository;
    private CellService cellService;



    public GameServiceImpl(Lobby lobby){
        this.game = new Game(careerCardRepository, actionCardRepository, houseCardRepository, cellService);
    }

    @Override
    public void startGame(LobbyDTO lobbyDTO) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void choosePath(PlayerDTO playerDTO) {
        //review: should this be implemented in GameService or PlayerService?
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void handleCell(PlayerDTO playerDTO, CellDTO cellDTO) {
        //review: should this be implemented in GameService or PlayerService?
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void getPlayerStats(LobbyDTO lobbyDTO) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void checkWinCondition(PlayerDTO playerDTO) {
        //review: not implemented yet, but method definition exists in Game.java --> move method?
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void spinWheel(PlayerDTO playerDTO) {
        //review: implemented in Game.java AND in PlayerService.java --> move method?
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void nextPlayer(PlayerDTO playerDTO) {
        //review: implemented in as nextTurn() in Game.java --> move method?
        throw new UnsupportedOperationException("Not implemented yet.");
    }


}
