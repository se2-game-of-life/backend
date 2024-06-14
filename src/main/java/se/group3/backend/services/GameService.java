package se.group3.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.Cell;
import se.group3.backend.domain.CellType;
import se.group3.backend.domain.Lobby;
import se.group3.backend.domain.Player;
import se.group3.backend.domain.cards.ActionCard;
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
    private final int INVESTMENT_COLLEGE = 100000;

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

        lobby.setHouseCards(new ArrayList<>());
        lobby.setCareerCards(new ArrayList<>());
        lobby.setActionCards(new ArrayList<>());
        lobby.setHasDecision(false);
        makeMove(lobby, player);

        lobbyRepository.save(lobby);
        playerRepository.save(player);
        updatePlayerInLobby(lobby, player);
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

        Cell cell = cellRepository.findByNumber(player.getCurrentCellPosition());
        if(cell == null){
            careerOrCollegeChoice(player, chooseLeft);
        } else{
            if(!Objects.equals(lobby.getCurrentPlayer().getPlayerUUID(), uuid)) throw new IllegalArgumentException("It's not the player's turn!");
            switch(cell.getType()){
                case MARRY, GROW_FAMILY:
                    marryAndFamilyPathChoice(player, chooseLeft, cell);
                    break;
                case RETIRE_EARLY:
                    if(chooseLeft) {
                        player.setCurrentCellPosition(cell.getNextCells().get(0));
                    } else {
                        player.setCurrentCellPosition(cell.getNextCells().get(1));
                    }
                    break;
                case HOUSE:
                    houseChoice(player, lobby, chooseLeft);
                    break;
                case CAREER:
                    careerChoice(player, lobby, chooseLeft);
                    break;
                case NOTHING:
                    lobby.nextPlayer();
                    break;
                case TELEPORT:
                    if (chooseLeft) {
                        int newPosition = player.getCurrentCellPosition() + 2;
                        player.setCurrentCellPosition(newPosition);
                    }
                    lobby.nextPlayer();
                    break;
                default:
                    throw new IllegalStateException("Unknown cell type." + cell.getType());
            }
        }

        lobby.nextPlayer();
        lobby.setHasDecision(false);
        updatePlayerInLobby(lobby, player);
        lobbyRepository.save(lobby);
        playerRepository.save(player);
        return LobbyMapper.toLobbyDTO(lobby);
    }

    private void updatePlayerInLobby(Lobby lobby, Player player){
        List<Player> players = lobby.getPlayers();
        List<Player> updatesPlayers = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getPlayerUUID().equals(player.getPlayerUUID())){
                updatesPlayers.add(i, player);
            } else{
                updatesPlayers.add(i, players.get(i));
            }
            if(updatesPlayers.get(i).getPlayerUUID().equals(lobby.getCurrentPlayer().getPlayerUUID())){
                lobby.setCurrentPlayer(updatesPlayers.get(i));
            }
        }
        lobby.setPlayers(updatesPlayers);
    }

    private void careerOrCollegeChoice(Player player, boolean chooseLeft){
            if(chooseLeft) {
                player.setMoney(player.getMoney() - INVESTMENT_COLLEGE);
                player.setCurrentCellPosition(1);
            } else {
                player.setCurrentCellPosition(14);
                player.setCareerCard(careerCardRepository.findCareerCardNoDiploma());
            }
            player.setCollegeDegree(chooseLeft);
    }

    private void marryAndFamilyPathChoice(Player player, boolean chooseLeft, Cell cell){
        if(chooseLeft){
            player.setMoney(player.getMoney()- INVESTMENT_MARRY_OR_FAMILY);
            player.setNumberOfPegs(player.getNumberOfPegs() + 1);
            player.setCurrentCellPosition(cell.getNextCells().get(0));
        } else{
            player.setCurrentCellPosition(cell.getNextCells().get(1));
        }
    }

    private void houseChoice(Player player, Lobby lobby, boolean chooseLeft){
        List<HouseCard> houseCardList = lobby.getHouseCards();
        HouseCard houseCard;
        if(chooseLeft){
            houseCard = houseCardList.get(0);
        } else{
            houseCard = houseCardList.get(1);
        }
        player.setMoney(player.getMoney()-houseCard.getPurchasePrice());
        if(player.getHouses() != null){
            List<HouseCard> playerHouseCards = player.getHouses();
            playerHouseCards.add(houseCard);
            player.setHouses(playerHouseCards);
        } else{
            player.setHouses(List.of(houseCard));
        }
    }

    private void careerChoice(Player player, Lobby lobby, boolean chooseLeft){
        List<CareerCard> careerCardList = lobby.getCareerCards();
        CareerCard careerCard;
        if(chooseLeft){
            careerCard = careerCardList.get(0);
        } else{
            careerCard = careerCardList.get(1);
        }
        player.setCareerCard(careerCard);
    }



    private void makeMove(Lobby lobby, Player player) {
        Cell currentCell = cellRepository.findByNumber(player.getCurrentCellPosition());
        for(int i = 0; i < lobby.getSpunNumber() - 1; i++) {
            List<Integer> nextCellNumbers = currentCell.getNextCells();
            if(nextCellNumbers.size() != 1) {
                break;
            }
            currentCell = cellRepository.findByNumber(nextCellNumbers.get(0));
            if(currentCell.getType() == CellType.CASH) {
                player.setMoney(player.getMoney() + player.getCareerCard().getSalary());
            } else if(currentCell.getType() == CellType.GRADUATE) {
                if (spinWheel() <= 2) {
                    player.setCollegeDegree(false);
                    player.setCareerCard(careerCardRepository.findCareerCardNoDiploma());
                } else{
                    player.setCollegeDegree(true);
                    player.setCareerCard(careerCardRepository.findCareerCardDiploma());
                }
                break;
            }
        }
        player.setCurrentCellPosition(currentCell.getNumber());
        playerRepository.save(player);
        lobbyRepository.save(lobby);
        handleCell(lobby, player, currentCell);
    }

    private void handleCell(Lobby lobby, Player player, Cell cell) {
        switch(cell.getType()) {
            case CASH:
                player.setMoney(player.getMoney() + player.getCareerCard().getBonus());
                lobby.nextPlayer();
                break;
            case ACTION:
                ActionCard randomActionCard = actionCardRepository.findRandomActionCard();
                doAction(player, randomActionCard);
                List<ActionCard> cardList = lobby.getActionCards();
                cardList.add(randomActionCard);
                lobby.setActionCards(cardList);
                lobby.nextPlayer();
                break;
            case FAMILY:
                player.setNumberOfPegs(player.getNumberOfPegs()+1);
                lobby.nextPlayer();
                break;
            case HOUSE:
                List<HouseCard> houseCards = houseCardRepository.searchAffordableHousesForPlayer(player.getMoney());
                if(houseCards.size() != 2) {
                    lobby.setHasDecision(false);
                    break;
                } else{
                    lobby.setHouseCards(houseCards);
                    lobby.setHasDecision(true);
                }
                break;
            case CAREER:
                List<CareerCard> careerCards = new ArrayList<>();
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
                lobby.setCareerCards(careerCards);
                lobby.setHasDecision(true);
                break;
            case MID_LIFE:
                if(spinWheel() > 2) {
                    player.setCurrentCellPosition(cell.getNextCells().get(0));
                } else {
                    player.setCurrentCellPosition(cell.getNextCells().get(1));
                }
                lobby.nextPlayer();
                break;
            case MARRY, GROW_FAMILY, RETIRE_EARLY:
                lobby.setHasDecision(true);
                break;
            case RETIREMENT:
                retire(player, lobby);
                break;
            case NOTHING:
                lobby.nextPlayer();
                break;
            default:
                lobby.nextPlayer();
        }
        playerRepository.save(player);
        lobbyRepository.save(lobby);
    }

    private void retire(Player player, Lobby lobby){
        List<Player> players = lobby.getPlayers();
        int counter = 0;
        for(Player p : players){
            Cell currentCell = cellRepository.findByNumber(p.getCurrentCellPosition());
            if(currentCell.getType() == CellType.RETIREMENT){
                counter++;
            }
        }

        switch (counter){
            case(1):
                player.setMoney(player.getMoney()+200000);
                break;
            case(2):
                player.setMoney(player.getMoney()+100000);
                break;
            case(3):
                player.setMoney(player.getMoney()+50000);
                break;
            case(4):
                player.setMoney(player.getMoney()+10000);
                break;
            default:
        }

        List<HouseCard> playerHouses = player.getHouses();
        for(HouseCard h : playerHouses){
            if(spinWheel()%2 == 0){
                player.setMoney(player.getMoney()+h.getBlackSellPrice());
            } else{
                player.setMoney(player.getMoney()+h.getRedSellPrice());
            }
        }
        player.setHouses(new ArrayList<>());

        player.setMoney(player.getMoney()+(player.getNumberOfPegs()*50000));

        List<Player> queue = lobby.getQueue();
        ArrayList<Player> newQueue = new ArrayList<>();
        for(Player p: queue){
            Cell currentCell = cellRepository.findByNumber(p.getCurrentCellPosition());
            if(currentCell.getType() != CellType.RETIREMENT){
                newQueue.add(p);
            }
        }
        lobby.setQueue(newQueue);
        //todo before queue is empty --> last player has to end the game
    }

    private void doAction(Player player, ActionCard actionCard) {
        if(actionCard.isAffectOnePlayer()) {
            player.setMoney(player.getMoney() + actionCard.getMoneyAmount());
        } else if(actionCard.isAffectAllPlayers()) {
            List<Player> players = playerRepository.findAll();
            for(Player p : players) {
                p.setMoney(p.getMoney() + actionCard.getMoneyAmount());
            }
        } else if(actionCard.isAffectBank()) {
            player.setMoney(player.getMoney() + actionCard.getMoneyAmount());
        }
    }

    private int spinWheel() {
        return RANDOM.nextInt(10) + 1;
    }
}
