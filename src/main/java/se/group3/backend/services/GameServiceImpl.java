package se.group3.backend.services;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.game.Game;
import se.group3.backend.domain.player.Player;
import se.group3.backend.domain.player.PlayerStatistic;
import se.group3.backend.dto.CellDTO;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.dto.mapper.PlayerMapper;
import se.group3.backend.repositories.ActionCardRepository;
import se.group3.backend.repositories.CareerCardRepository;
import se.group3.backend.repositories.CellRepository;
import se.group3.backend.repositories.HouseCardRepository;
import se.group3.backend.repositories.player.PlayerRepository;

import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of the Interface GameService
 */
@Slf4j
@Service
@NoArgsConstructor
public class GameServiceImpl implements GameService {

    private Game game;

    private CareerCardRepository careerCardRepository;
    private ActionCardRepository actionCardRepository;
    private HouseCardRepository houseCardRepository;
    private CellRepository cellRepository;
    private PlayerRepository playerRepository;



    public GameServiceImpl(CareerCardRepository careerCardRepository, ActionCardRepository actionCardRepository, HouseCardRepository houseCardRepository, CellRepository cellRepository, PlayerRepository playerRepository){
        this.careerCardRepository = careerCardRepository;
        this.actionCardRepository = actionCardRepository;
        this.houseCardRepository = houseCardRepository;
        this.cellRepository = cellRepository;
        this.playerRepository = playerRepository;
        this.game = new Game(careerCardRepository, actionCardRepository, houseCardRepository, cellRepository);
    }


    @Override
    public void startGame(LobbyDTO lobbyDTO) {
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
    public List<PlayerStatistic> getPlayerStats(PlayerDTO playerDTO, LobbyDTO lobbyDTO) {
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
