package se.group3.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.Cell;
import se.group3.backend.domain.CellType;
import se.group3.backend.domain.Lobby;
import se.group3.backend.domain.Player;
import se.group3.backend.domain.cards.ActionCard;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.mapper.LobbyMapper;
import se.group3.backend.repositories.*;
import se.group3.backend.repositories.PlayerRepository;

import java.util.*;

@Slf4j
@Service
public class GameService {

    private final CareerCardRepository careerCardRepository;
    private final ActionCardRepository actionCardRepository;
    private final CellRepository cellRepository;
    private final PlayerRepository playerRepository;
    private final LobbyRepository lobbyRepository;
    private final PlayerService playerService;

    private static final Random RANDOM = new Random();
    private static final String PLAYER_NOT_FOUND = "Player not found!";
    private static final String PLAYER_NOT_IN_LOBBY = "Player not in lobby!";
    private static final String LOBBY_NOT_FOUND = "Lobby not found!";
    private static final String NOT_PLAYERS_TURN = "It's not the player's turn!";

    @Autowired
    public GameService(CareerCardRepository careerCardRepository, ActionCardRepository actionCardRepository, HouseCardRepository houseCardRepository, CellRepository cellRepository, PlayerRepository playerRepository, LobbyRepository lobbyRepository){
        this.careerCardRepository = careerCardRepository;
        this.actionCardRepository = actionCardRepository;
        this.cellRepository = cellRepository;
        this.playerRepository = playerRepository;
        this.lobbyRepository = lobbyRepository;
        this.playerService = new PlayerService(careerCardRepository, cellRepository, houseCardRepository);
    }

    public LobbyDTO handleTurn(String playerUUID) throws IllegalArgumentException {
        Player player = getPlayerFromDB(playerUUID);

        Long lobbyID = player.getLobbyID();
        if(lobbyID == null) throw new IllegalArgumentException(PLAYER_NOT_IN_LOBBY);

        Lobby lobby = getLobbyFromDB(lobbyID);

        if(!Objects.equals(lobby.getCurrentPlayer().getPlayerUUID(), playerUUID)) throw new IllegalArgumentException(NOT_PLAYERS_TURN);
        lobby.setSpunNumber(spinWheel());

        lobby.setHouseCards(new ArrayList<>());
        lobby.setCareerCards(new ArrayList<>());
        lobby.setActionCards(new ArrayList<>());
        lobby.setHasDecision(false);
        makeMove(lobby, player);

        lobbyRepository.save(lobby);
        playerRepository.save(player);
        lobby.updatePlayerInLobby(player);
        return LobbyMapper.toLobbyDTO(lobby);
    }

    public LobbyDTO makeChoice(boolean chooseLeft, String uuid) {
        Player player = getPlayerFromDB(uuid);

        Long lobbyID = player.getLobbyID();
        if(lobbyID == null) throw new IllegalArgumentException(PLAYER_NOT_IN_LOBBY);

        Lobby lobby = getLobbyFromDB(lobbyID);

        Cell cell = cellRepository.findByNumber(player.getCurrentCellPosition());
        if(cell == null){
            playerService.careerOrCollegeChoice(player, chooseLeft);
            lobby.updatePlayerInLobby(player);
            lobby.nextPlayer();
        } else{
            if(!Objects.equals(lobby.getCurrentPlayer().getPlayerUUID(), uuid)) throw new IllegalArgumentException(NOT_PLAYERS_TURN);
            switch(cell.getType()){
                case MARRY, GROW_FAMILY:
                    playerService.marryAndFamilyPathChoice(player, chooseLeft, cell);
                    lobby.updatePlayerInLobby(player);
                    lobby.nextPlayer();
                    break;
                case RETIRE_EARLY:
                    playerService.retireEarly(player, cell, chooseLeft);
                    lobby.updatePlayerInLobby(player);
                    lobby.nextPlayer();
                    break;
                case HOUSE:
                    playerService.houseChoice(player, lobby, chooseLeft);
                    lobby.updatePlayerInLobby(player);
                    lobby.nextPlayer();
                    lobby.setHouseCards(new ArrayList<>());
                    break;
                case CAREER:
                    playerService.careerChoice(player, lobby, chooseLeft);
                    lobby.updatePlayerInLobby(player);
                    lobby.nextPlayer();
                    lobby.setCareerCards(new ArrayList<>());
                    break;
                case NOTHING:
                    lobby.updatePlayerInLobby(player);
                    lobby.nextPlayer();
                    break;
                case TELEPORT:
                    if (chooseLeft) {
                        int newPosition = player.getCurrentCellPosition() + 2;
                        player.setCurrentCellPosition(newPosition);
                    }
                    lobby.updatePlayerInLobby(player);
                    lobby.nextPlayer();
                    break;
                default:
                    throw new IllegalStateException("Unknown cell type." + cell.getType());
            }
        }
        lobby.setHasDecision(false);
        lobbyRepository.save(lobby);
        playerRepository.save(player);
        return LobbyMapper.toLobbyDTO(lobby);
    }

    public LobbyDTO endGameEarlier(String playerUUID) throws IllegalArgumentException {
        Player player = getPlayerFromDB(playerUUID);

        Long lobbyID = player.getLobbyID();
        if(lobbyID == null) throw new IllegalArgumentException(PLAYER_NOT_IN_LOBBY);

        Lobby lobby = getLobbyFromDB(lobbyID);

        if(!Objects.equals(lobby.getCurrentPlayer().getPlayerUUID(), playerUUID)) throw new IllegalArgumentException(NOT_PLAYERS_TURN);
        lobby.setSpunNumber(spinWheel());

        List<Player> players = new ArrayList<>(lobby.getPlayers());

        if(players.size() > 1) {
            players.sort(Comparator.comparingDouble(Player::getCurrentCellPosition).reversed());
        }

        for (Player p : players){
            p.setCurrentCellPosition(123);
            playerService.retire(p, lobby, lobby.getSpunNumber());
            lobby.updatePlayerInLobby(p);
        }

        lobby.setHasStarted(false);
        lobbyRepository.save(lobby);
        playerRepository.save(player);
        return LobbyMapper.toLobbyDTO(lobby);
    }


    private void makeMove(Lobby lobby, Player player) {
        Cell currentCell = cellRepository.findByNumber(player.getCurrentCellPosition());
        for(int i = 0; i < lobby.getSpunNumber(); i++) {
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
            } else if(currentCell.getType() == CellType.RETIREMENT){
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
                lobby.updatePlayerInLobby(player);
                lobby.nextPlayer();
                break;
            case ACTION:
                ActionCard randomActionCard = actionCardRepository.findRandomActionCard();
                doAction(player, randomActionCard);
                List<ActionCard> cardList = lobby.getActionCards();
                cardList.add(randomActionCard);
                lobby.setActionCards(cardList);
                lobby.updatePlayerInLobby(player);
                lobby.nextPlayer();
                break;
            case FAMILY:
                player.setNumberOfPegs(player.getNumberOfPegs()+1);
                lobby.updatePlayerInLobby(player);
                lobby.nextPlayer();
                break;
            case HOUSE:
                playerService.getHouseCards(player, lobby);
                lobby.setHasDecision(true);
                lobby.updatePlayerInLobby(player);
                break;
            case CAREER:
                playerService.getCareerCards(player, lobby);
                lobby.setHasDecision(true);
                lobby.updatePlayerInLobby(player);
                break;
            case MID_LIFE:
                playerService.midLife(player, cell, spinWheel());
                lobby.setHasDecision(false);
                lobby.updatePlayerInLobby(player);
                lobby.nextPlayer();
                break;
            case MARRY, GROW_FAMILY, RETIRE_EARLY:
                lobby.setHasDecision(true);
                lobby.updatePlayerInLobby(player);
                break;
            case RETIREMENT:
                playerService.retire(player, lobby, spinWheel());
                lobby.updatePlayerInLobby(player);
                lobby.nextPlayerRetired();
                break;
            case NOTHING:
                lobby.updatePlayerInLobby(player);
                lobby.nextPlayer();
                break;
            case TELEPORT:
                lobby.setHasDecision(true);
                break;
            default:
                lobby.updatePlayerInLobby(player);
                lobby.nextPlayer();
        }
        playerRepository.save(player);
        lobbyRepository.save(lobby);
    }


    public void doAction(Player player, ActionCard actionCard) {
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

    private Player getPlayerFromDB(String playerUUID){
        Optional<Player> playerOptional = playerRepository.findById(playerUUID);
        if(playerOptional.isEmpty()) throw new IllegalArgumentException(PLAYER_NOT_FOUND);
        return playerOptional.get();
    }

    private Lobby getLobbyFromDB(Long lobbyID){
        Optional<Lobby> lobbyOptional = lobbyRepository.findById(lobbyID);
        if(lobbyOptional.isEmpty()) throw new IllegalArgumentException(LOBBY_NOT_FOUND);
        return lobbyOptional.get();
    }

}
