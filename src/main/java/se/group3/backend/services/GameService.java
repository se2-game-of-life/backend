package se.group3.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.Cell;
import se.group3.backend.domain.CellType;
import se.group3.backend.domain.Lobby;
import se.group3.backend.domain.Player;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.mapper.LobbyMapper;
import se.group3.backend.repositories.*;
import se.group3.backend.repositories.PlayerRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class GameService {

    private CareerCardRepository careerCardRepository;
    private ActionCardRepository actionCardRepository;
    private HouseCardRepository houseCardRepository;
    private CellRepository cellRepository;
    private PlayerRepository playerRepository;
    private LobbyRepository lobbyRepository;

    private static final Random RANDOM = new Random();

    @Autowired
    public GameService(CareerCardRepository careerCardRepository, ActionCardRepository actionCardRepository, HouseCardRepository houseCardRepository, CellRepository cellRepository, PlayerRepository playerRepository, LobbyRepository lobbyRepository){
        this.careerCardRepository = careerCardRepository;
        this.actionCardRepository = actionCardRepository;
        this.houseCardRepository = houseCardRepository;
        this.cellRepository = cellRepository;
        this.playerRepository = playerRepository;
        this.lobbyRepository = lobbyRepository;
    }

    public LobbyDTO handleTurn(String playerUUID) throws IllegalArgumentException {
        Optional<Player> playerOptional = playerRepository.findById(playerUUID);
        if(playerOptional.isEmpty()) throw new IllegalArgumentException("Player not found!");
        Player player = playerOptional.get();

        Long lobbyID = player.getLobbyID();
        if(lobbyID == null) throw new IllegalArgumentException("Player not in lobby!");

        Optional<Lobby> lobbyOptional = lobbyRepository.findById(player.getLobbyID());
        if(lobbyOptional.isEmpty()) throw new IllegalArgumentException("Lobby not found!");
        Lobby lobby = lobbyOptional.get();

        if(!Objects.equals(lobby.getCurrentPlayer().getPlayerUUID(), playerUUID)) throw new IllegalArgumentException("It's not the player's turn!");
        lobby.setSpunNumber(spinWheel());

        makeMove(lobby, player);

        lobbyRepository.save(lobby);
        playerRepository.save(player);
        return LobbyMapper.toLobbyDTO(lobby);
    }

    public LobbyDTO makeChoice(boolean chooseLeft, String uuid) {
        //todo: implement choices
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    private void makeMove(Lobby lobby, Player player) {
        Cell currentCell = cellRepository.findByNumber(player.getCurrentCellPosition());
        for(int i = 0; i < lobby.getSpunNumber() - 1; i++) {
            List<Integer> nextCellNumbers = currentCell.getNextCells();
            if(nextCellNumbers.size() > 1) {
                //todo: handle stop cell
            }
            //todo: handle final stop cell
            currentCell = cellRepository.findByNumber(nextCellNumbers.get(0)); //get next cell
            if(currentCell.getType() == CellType.CASH) {
                player.setMoney(player.getMoney() + player.getCareerCard().getSalary());
            }
        }
        handleCell(lobby, player, currentCell);
    }

    private void handleCell(Lobby lobby, Player player, Cell cell) {
        switch(cell.getType()) {
            case CASH:
                player.setMoney(player.getMoney() + player.getCareerCard().getBonus());
                lobby.nextPlayer();
                break;
            case ACTION:
                lobby.nextPlayer();
                break;
            case FAMILY:
                lobby.nextPlayer();
                break;
            case HOUSE:
                //not next player because is player has choice
                break;
            case CAREER:
                lobby.nextPlayer();
                break;
            default:
                log.error("Cell type unknown!");
        }
    }

    private int spinWheel() {
        return RANDOM.nextInt(10) + 1;
    }
}
