package se.group3.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.group3.backend.DTOs.PlayerDTO;
import se.group3.backend.domain.game.Game;
import se.group3.backend.domain.player.Player;
import se.group3.backend.domain.player.PlayerStatistic;
import se.group3.backend.dto.CellDTO;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.LobbyDTOtemp;
import se.group3.backend.mapper.PlayerMapper;
import se.group3.backend.repositories.ActionCardRepository;
import se.group3.backend.repositories.CareerCardRepository;
import se.group3.backend.repositories.HouseCardRepository;
import se.group3.backend.repositories.player.PlayerRepository;

import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of the Interface GameService
 */
@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final Game game;

    private final CareerCardRepository careerCardRepository;
    private final ActionCardRepository actionCardRepository;
    private final HouseCardRepository houseCardRepository;
    private final CellService cellService;
    private final PlayerRepository playerRepository;



    public GameServiceImpl(CareerCardRepository careerCardRepository, ActionCardRepository actionCardRepository, HouseCardRepository houseCardRepository, CellService cellService, PlayerRepository playerRepository){
        this.careerCardRepository = careerCardRepository;
        this.actionCardRepository = actionCardRepository;
        this.houseCardRepository = houseCardRepository;
        this.cellService = cellService;
        this.playerRepository = playerRepository;
        this.game = new Game(careerCardRepository, actionCardRepository, houseCardRepository, cellService);
    }

    @Override
    public void startGame(LobbyDTO lobbyDTO) {
        game.initializeBoard();
        game.initializeDecks();
        throw new UnsupportedOperationException();
    }

    @Override
    public void choosePath(PlayerDTO playerDTO) {
        //review: should this be implemented in GameService or PlayerService?
        throw new UnsupportedOperationException();
    }

    @Override
    public void handleCell(PlayerDTO playerDTO, CellDTO cellDTO) {
        //review: should this be implemented in GameService or PlayerService?
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PlayerStatistic> getPlayerStats(PlayerDTO playerDTO, LobbyDTOtemp lobbyDTO) {
        PlayerDTO[] players = lobbyDTO.getPlayers();
        List<PlayerStatistic> otherPlayersStats = new ArrayList<>();
        for (PlayerDTO dto : players) {
            if (!dto.equals(playerDTO)) {
                Player player = PlayerMapper.mapDTOToPlayer(dto);
                otherPlayersStats.add(new PlayerStatistic(player));
            }
        }
        return otherPlayersStats;
    }

    @Override
    public void checkWinCondition(PlayerDTO playerDTO) {
        //review: not implemented yet, but method definition exists in Game.java --> move method?
        throw new UnsupportedOperationException();
    }

    @Override
    public void spinWheel(PlayerDTO playerDTO) {
        //review: implemented in Game.java AND in PlayerService.java --> move method?
        throw new UnsupportedOperationException();
    }

    @Override
    public void nextPlayer(PlayerDTO playerDTO) {
        //review: implemented in as nextTurn() in Game.java --> move method?
        throw new UnsupportedOperationException();
    }


}
