package se.group3.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.repositories.*;
import se.group3.backend.repositories.player.PlayerRepository;

@Slf4j
@Service
public class GameService {

    private CareerCardRepository careerCardRepository;
    private ActionCardRepository actionCardRepository;
    private HouseCardRepository houseCardRepository;
    private CellRepository cellRepository;
    private PlayerRepository playerRepository;
    private LobbyRepository lobbyRepository;

    @Autowired
    public GameService(CareerCardRepository careerCardRepository, ActionCardRepository actionCardRepository, HouseCardRepository houseCardRepository, CellRepository cellRepository, PlayerRepository playerRepository, LobbyRepository lobbyRepository lobbyRepository){
        this.careerCardRepository = careerCardRepository;
        this.actionCardRepository = actionCardRepository;
        this.houseCardRepository = houseCardRepository;
        this.cellRepository = cellRepository;
        this.playerRepository = playerRepository;
        this.lobbyRepository = lobbyRepository;
    }

    public LobbyDTO handleTurn() {
        return new LobbyDTO();
    }

    public LobbyDTO makeChoice() {
        return new LobbyDTO();
    }

//    public String choosePath(String playerUUID, boolean collegePath) {
//        try {
//            PlayerDTO playerDTO = (PlayerDTO) SerializationUtil.toObject(playerUUID, PlayerDTO.class);
//            playerDTO.setCollegePath(collegePath);
//            if(playerRepository.findById(playerDTO.getPlayerID()).isPresent()) {
//                Player player = playerRepository.findById(playerDTO.getPlayerID()).get();
//                player.setCollegeDegree(collegePath);
//                if(playerDTO.isCollegePath()){
//                    log.debug("Player chose college path.");
//                    playerDTO.setMoney(150000);
//                    log.debug("Player paid 100k for college.");
//                    player.setCollegeDegree(true);
//                    player.setMoney(150000);
//                    playerRepository.save(player);
//                }
//            }
//            return SerializationUtil.jsonStringFromClass(playerDTO);
//        } catch (JsonProcessingException e) {
//            log.error(e.getMessage());
//        }
//        return playerUUID;
//    }
}
