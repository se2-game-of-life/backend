package se.group3.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.Cell;
import se.group3.backend.domain.CellType;
import se.group3.backend.domain.Lobby;
import se.group3.backend.domain.Player;
import se.group3.backend.domain.cards.Card;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.mapper.LobbyMapper;
import se.group3.backend.repositories.*;
import se.group3.backend.repositories.PlayerRepository;

import java.util.*;

@Slf4j
@Service
public class GameService {

    private CareerCardRepository careerCardRepository;
    private ActionCardRepository actionCardRepository;
    private HouseCardRepository houseCardRepository;
    private CellRepository cellRepository;
    private PlayerRepository playerRepository;
    private LobbyRepository lobbyRepository;
    private final int INVESTMENT_MARRY_OR_FAMILY = 50000;

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
        Optional<Player> playerOptional = playerRepository.findById(uuid);
        if(playerOptional.isEmpty()) throw new IllegalArgumentException("Player not found!");
        Player player = playerOptional.get();

        Long lobbyID = player.getLobbyID();
        if(lobbyID == null) throw new IllegalArgumentException("Player not in lobby!");

        Optional<Lobby> lobbyOptional = lobbyRepository.findById(player.getLobbyID());
        if(lobbyOptional.isEmpty()) throw new IllegalArgumentException("Lobby not found!");
        Lobby lobby = lobbyOptional.get();

        if(!Objects.equals(lobby.getCurrentPlayer().getPlayerUUID(), uuid)) throw new IllegalArgumentException("It's not the player's turn!");


        Cell cell = cellRepository.findByNumber(player.getCurrentCellPosition());

        switch(cell.getType()){
            case MARRY, GROW_FAMILY:
                if(chooseLeft){
                    player.setMoney(player.getMoney()- INVESTMENT_MARRY_OR_FAMILY);
                    player.setNumberOfPegs(player.getNumberOfPegs() + 1);
                }
                //todo: select next cell
                lobby.nextPlayer();
                break;
            case RETIRE_EARLY:
                //todo: select next cell
                throw new UnsupportedOperationException("Not implemented yet!");
            case HOUSE:
                List<Card> houseCardList = lobby.getCards();
                HouseCard houseCard;
                if(chooseLeft){
                    houseCard = (HouseCard) houseCardList.get(0);
                } else{
                    houseCard = (HouseCard) houseCardList.get(1);
                }
                List<HouseCard> playerHouseCards = player.getHouses();
                playerHouseCards.add(houseCard);
                lobby.nextPlayer();
                break;
            case CAREER:
                List<Card> careerCardList = lobby.getCards();
                CareerCard careerCard;
                if(chooseLeft){
                    careerCard = (CareerCard) careerCardList.get(0);
                } else{
                    careerCard = (CareerCard) careerCardList.get(1);
                }
                player.setCareerCard(careerCard);
                lobby.nextPlayer();
                break;
            default:
                careerOrCollegeChoice(player, chooseLeft);
        }
        lobbyRepository.save(lobby);
        playerRepository.save(player);
        return LobbyMapper.toLobbyDTO(lobby);
    }

    private void careerOrCollegeChoice(Player player, boolean chooseLeft){
        if(player.getCurrentCellPosition() == 0){
            if(chooseLeft){
                player.setMoney(150000);
            }
            player.setCollegeDegree(chooseLeft);
        }
    }


    private void makeMove(Lobby lobby, Player player) {
        Cell currentCell = cellRepository.findByNumber(player.getCurrentCellPosition());
        boolean stopCell = false;
        for(int i = 0; i < lobby.getSpunNumber() - 1; i++) {
            List<Integer> nextCellNumbers = currentCell.getNextCells();
            if(nextCellNumbers.size() > 1) {
                stopCell = true;
            }
            //todo: handle final stop cell
            currentCell = cellRepository.findByNumber(nextCellNumbers.get(0)); //get next cell
            if(currentCell.getType() == CellType.CASH) {
                player.setMoney(player.getMoney() + player.getCareerCard().getSalary());
            }
            if (stopCell){
                break;
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
                lobby.setCards(List.of(actionCardRepository.findRandomActionCard()));
                lobby.nextPlayer();
                break;
            case FAMILY:
                player.setNumberOfPegs(player.getNumberOfPegs()+1);
                lobby.nextPlayer();
                break;
            case HOUSE:
                List<Card> houseCards = new ArrayList<>();
                houseCards.add(houseCardRepository.findRandomHouseCard());
                houseCards.add(houseCardRepository.findRandomHouseCard());
                lobby.setCards(houseCards);
                lobby.setHasDecision(true);
                break;
            case CAREER:
                List<Card> careerCards = new ArrayList<>();
                if(player.isCollegeDegree()){
                    careerCards.add(careerCardRepository.findRandomCareerCard());
                    careerCards.add(careerCardRepository.findRandomCareerCard());
                } else{
                    while(careerCards.size() < 2){
                        CareerCard card = careerCardRepository.findRandomCareerCard();
                        if(!card.needsDiploma()){
                            careerCards.add(card);
                        }
                    }
                }
                lobby.setHasDecision(true);
                break;
            case GRADUATE:
                player.setCollegeDegree(spinWheel() % 2 == 0);
                lobby.nextPlayer();
                break;
            case MID_LIFE:
                player.setCollegeDegree(spinWheel() % 2 != 0);
                lobby.nextPlayer();
                break;
            case MARRY:
                lobby.setHasDecision(true);
                break;
            case GROW_FAMILY:
                lobby.setHasDecision(true);
                break;
            case RETIRE_EARLY:
                lobby.setHasDecision(true);
                break;
            default:
                log.error("Cell type unknown!");
        }
    }

    private int spinWheel() {
        return RANDOM.nextInt(10) + 1;
    }
}
