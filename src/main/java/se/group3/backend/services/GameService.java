package se.group3.backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.game.Game;
import se.group3.backend.domain.player.Player;
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

@Slf4j
@Service
public class GameService {

    private CareerCardRepository careerCardRepository;
    private ActionCardRepository actionCardRepository;
    private HouseCardRepository houseCardRepository;
    private CellRepository cellRepository;
    private PlayerRepository playerRepository;

    public GameService(CareerCardRepository careerCardRepository, ActionCardRepository actionCardRepository, HouseCardRepository houseCardRepository, CellRepository cellRepository, PlayerRepository playerRepository){
        this.careerCardRepository = careerCardRepository;
        this.actionCardRepository = actionCardRepository;
        this.houseCardRepository = houseCardRepository;
        this.cellRepository = cellRepository;
        this.playerRepository = playerRepository;
    }


    public void startGame(LobbyDTO lobbyDTO) {
        throw new UnsupportedOperationException();
    }


    public String choosePath(String playerUUID, boolean collegePath) {
        try {
            PlayerDTO playerDTO = (PlayerDTO) SerializationUtil.toObject(playerUUID, PlayerDTO.class);
            playerDTO.setCollegePath(collegePath);
            if(playerRepository.findById(playerDTO.getPlayerID()).isPresent()) {
                Player player = playerRepository.findById(playerDTO.getPlayerID()).get();
                player.setCollegeDegree(collegePath);
                if(playerDTO.isCollegePath()){
                    log.debug("Player chose college path.");
                    playerDTO.setMoney(150000);
                    log.debug("Player paid 100k for college.");
                    player.setCollegeDegree(true);
                    player.setMoney(150000);
                    playerRepository.save(player);
                }
            }
            return SerializationUtil.jsonStringFromClass(playerDTO);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return playerUUID;
    }


    public void handleCell(PlayerDTO playerDTO, CellDTO cellDTO) {
        //review: should this be implemented in GameService or PlayerService?
        throw new UnsupportedOperationException();
    }


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

    public void checkWinCondition(PlayerDTO playerDTO) {
        //review: not implemented yet, but method definition exists in Game.java --> move method?
        throw new UnsupportedOperationException();
    }

    public void spinWheel(PlayerDTO playerDTO) {
        //review: implemented in Game.java AND in PlayerService.java --> move method?
        throw new UnsupportedOperationException();
    }

    public void nextPlayer(PlayerDTO playerDTO) {
        //review: implemented in as nextTurn() in Game.java --> move method?
        throw new UnsupportedOperationException();
    }

}
