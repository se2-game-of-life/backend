package se.group3.backend.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import se.group3.backend.domain.Cell;
import se.group3.backend.domain.CellType;
import se.group3.backend.domain.Lobby;
import se.group3.backend.domain.Player;
import se.group3.backend.domain.cards.ActionCard;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;

import se.group3.backend.repositories.*;
import se.group3.backend.services.GameService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    private GameService gameService;
    private PlayerRepository playerRepository;
    private LobbyRepository lobbyRepository;
    private CellRepository cellRepository;
    private ActionCardRepository actionCardRepository;
    private CareerCardRepository careerCardRepository;
    private HouseCardRepository houseCardRepository;
    private Player player;
    private Lobby lobby;

    private final HouseCard houseCard1 = new HouseCard("House1","House1", 50000,0,0);
    private final HouseCard houseCard2 = new HouseCard("House2","House2", 10000,0,0);
    private final static CareerCard careerCard1 = new CareerCard("Career1","Career1", 100, 200, false);
    private final static CareerCard careerCard2 = new CareerCard("Career2", "Career2", 100, 200, false);


    @BeforeEach
    void setUp() {
        this.playerRepository = mock(PlayerRepository.class);
        this.lobbyRepository = mock(LobbyRepository.class);
        this.cellRepository = mock(CellRepository.class);
        this.actionCardRepository = mock(ActionCardRepository.class);
        this.houseCardRepository = mock(HouseCardRepository.class);
        this.careerCardRepository = mock(CareerCardRepository.class);
        this.gameService = new GameService(careerCardRepository, actionCardRepository, houseCardRepository, cellRepository, playerRepository, lobbyRepository);
        player = new Player();
        player.setCurrentCellPosition(0);
        player.setMoney(250000);
        player.setLobbyID(1L);
        player.setPlayerUUID("UUID");
        lobby = new Lobby(1L, player);
        lobby.setCurrentPlayer(player);
    }


    @Test
    void testMakeChoice_PlayerNotFound_Exception(){
        String uuid = player.getPlayerUUID();
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.empty());

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                gameService.makeChoice(true, uuid));

        assertEquals("Player not found!", e.getMessage());
    }

    @Test
    void testHandleTurn_PlayerNotFound_Exception(){
        String uuid = player.getPlayerUUID();
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.empty());

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                gameService.handleTurn(uuid));

        assertEquals("Player not found!", e.getMessage());
    }

    @Test
    void testEndGameEarlier_PlayerNotFound_Exception(){
        String uuid = player.getPlayerUUID();
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.empty());

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                gameService.endGameEarlier(uuid));

        assertEquals("Player not found!", e.getMessage());
    }


    @Test
    void testMakeChoice_PlayerNotInLobby_Exception(){
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        player.setLobbyID(null);
        String uuid = player.getPlayerUUID();

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                gameService.makeChoice(true, uuid));

        assertEquals("Player not in lobby!", e.getMessage());
    }

    @Test
    void testHandleTurn_PlayerNotInLobby_Exception(){
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        player.setLobbyID(null);
        String uuid = player.getPlayerUUID();

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                gameService.handleTurn(uuid));

        assertEquals("Player not in lobby!", e.getMessage());
    }

    @Test
    void testEndGameEarlier_PlayerNotInLobby_Exception(){
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        player.setLobbyID(null);
        String uuid = player.getPlayerUUID();

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                gameService.endGameEarlier(uuid));

        assertEquals("Player not in lobby!", e.getMessage());
    }

    @Test
    void testMakeChoice_LobbyNotFound_Exception(){
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        String uuid = player.getPlayerUUID();

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                gameService.makeChoice(true, uuid));

        assertEquals("Lobby not found!", e.getMessage());
    }

    @Test
    void testHandleTurn_LobbyNotFound_Exception(){
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        String uuid = player.getPlayerUUID();

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                gameService.handleTurn(uuid));

        assertEquals("Lobby not found!", e.getMessage());
    }

    @Test
    void testEndGameEarlier_LobbyNotFound_Exception(){
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        String uuid = player.getPlayerUUID();

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                gameService.endGameEarlier(uuid));

        assertEquals("Lobby not found!", e.getMessage());
    }

    @Test
    void testException_MakeChoice_NotThePlayersTurn(){
        Cell cell = mock(Cell.class);
        Player currentPlayer = new Player("123", "CurrentPlayer");
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));

        lobby.setCurrentPlayer(currentPlayer);
        String uuid = player.getPlayerUUID();

        Exception e = assertThrows(IllegalArgumentException.class, () ->
                gameService.makeChoice(true, uuid));

        assertEquals("It's not the player's turn!", e.getMessage());
    }

    @Test
    void testCareerOrCollegeChoice_CollegePath(){
        assertFalse(player.isCollegeDegree());

        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));

        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(null);

        gameService.makeChoice(true, "UUID");
        assertEquals(150000, player.getMoney());
        assertTrue(player.isCollegeDegree());
    }

    @Test
    void testCareerOrCollegeChoice_CareerPath(){
        assertFalse(player.isCollegeDegree());

        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(null);

        gameService.makeChoice(false, "UUID");
        assertEquals(250000, player.getMoney());
        assertFalse(player.isCollegeDegree());
    }

    @ParameterizedTest
    @EnumSource(value = CellType.class, names = { "MARRY", "GROW_FAMILY" })
    void makeChoice_Marry_or_GrowFamily_true(CellType type){
        assertFalse(player.isCollegeDegree());
        player.setNumberOfPegs(0);

        Cell startCell = mock(Cell.class);

        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));
        when(startCell.getType()).thenReturn(type);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);
        List<Integer> nextCells = List.of(1, 2);
        when(startCell.getNextCells()).thenReturn(nextCells);


        gameService.makeChoice(true, "UUID");
        assertEquals(200000, player.getMoney());
        assertEquals(1, player.getNumberOfPegs());
    }

    @ParameterizedTest
    @EnumSource(value = CellType.class, names = { "MARRY", "GROW_FAMILY" })
    void makeChoice_Marry_or_GrowFamily_false(CellType type){
        assertFalse(player.isCollegeDegree());

        player.setNumberOfPegs(0);

        Cell startCell = mock(Cell.class);

        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));
        when(startCell.getType()).thenReturn(type);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);
        List<Integer> nextCells = List.of(1, 2);
        when(startCell.getNextCells()).thenReturn(nextCells);


        gameService.makeChoice(false, "UUID");
        assertEquals(250000, player.getMoney());
        assertEquals(0, player.getNumberOfPegs());
    }

    @Test
    void testMakeChoice_Retire_Early_true(){
        Cell startCell = mock(Cell.class);
        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        player.setLobbyID(2L);
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(startCell.getType()).thenReturn(CellType.RETIRE_EARLY);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);
        List<Integer> nextCells = List.of(1, 2);
        when(startCell.getNextCells()).thenReturn(nextCells);


        gameService.makeChoice(true, "UUID");
        assertEquals(1, player.getCurrentCellPosition());
        verify(lobbyMock).updatePlayerInLobby(player);
        verify(lobbyMock).nextPlayer();
    }

    @Test
    void testMakeChoice_Retire_Early_false(){
        Cell startCell = mock(Cell.class);
        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        player.setLobbyID(2L);
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(startCell.getType()).thenReturn(CellType.RETIRE_EARLY);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);
        List<Integer> nextCells = List.of(1, 2);
        when(startCell.getNextCells()).thenReturn(nextCells);


        gameService.makeChoice(false, "UUID");
        assertEquals(2, player.getCurrentCellPosition());
        verify(lobbyMock).updatePlayerInLobby(player);
        verify(lobbyMock).nextPlayer();
    }

    @Test
    void testMakeChoice_NOTHING(){
        Cell startCell = mock(Cell.class);
        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        player.setLobbyID(2L);
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(startCell.getType()).thenReturn(CellType.NOTHING);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);

        gameService.makeChoice(false, "UUID");
        verify(lobbyMock).updatePlayerInLobby(player);
        verify(lobbyMock).nextPlayer();
    }

    @Test
    void testHandleCell_NOTHING(){
        Cell startCell = mock(Cell.class);
        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        player.setLobbyID(2L);
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(startCell.getType()).thenReturn(CellType.NOTHING);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);

        gameService.handleTurn("UUID");
        verify(lobbyMock, times(2)).updatePlayerInLobby(player);
        verify(lobbyMock).nextPlayer();
    }

    @Test
    void testMakeChoice_Default(){
        Cell startCell = mock(Cell.class);
        CellType type = mock(CellType.class);
        when(type.toString()).thenReturn("Something");


        when(lobbyRepository.findById(1L)).thenReturn(Optional.of(lobby));
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(startCell.getType()).thenReturn(type);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);

        String uuid = player.getPlayerUUID();

        Exception e = assertThrows(IllegalStateException.class, () ->
                gameService.makeChoice(true, uuid));

        assertEquals("Unknown cell type."+"Something", e.getMessage());
    }

    @Test
    void testHandleTurn_Default(){
        Cell startCell = mock(Cell.class);
        CellType type = mock(CellType.class);
        Lobby lobbyMock = mock(Lobby.class);

        player.setLobbyID(2L);
        player.setCareerCard(careerCard1);
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));

        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        when(startCell.getType()).thenReturn(type);
        when(cellRepository.findByNumber(anyInt())).thenReturn(startCell);

        gameService.handleTurn("UUID");

        verify(lobbyMock, times(2)).updatePlayerInLobby(player);
        verify(lobbyMock).nextPlayer();
    }

    @Test
    void testMakeChoice_Houses_true(){
        lobby.setHouseCards(List.of(houseCard1, houseCard2));
        Cell startCell = mock(Cell.class);

        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));
        when(startCell.getType()).thenReturn(CellType.HOUSE);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);


        gameService.makeChoice(true, "UUID");
        assertEquals(200000, player.getMoney());
        assertEquals(List.of(houseCard1), player.getHouses());
    }

    @Test
    void testMakeChoice_Houses_false(){
        lobby.setHouseCards(List.of(houseCard1, houseCard2));
        Cell startCell = mock(Cell.class);


        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));
        when(startCell.getType()).thenReturn(CellType.HOUSE);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);


        gameService.makeChoice(false, "UUID");
        assertEquals(240000, player.getMoney());
        assertEquals(List.of(houseCard2), player.getHouses());
    }

    @Test
    void testMakeChoice_TeleportCell_true() {
        // Arrange
        Cell startCell = mock(Cell.class);
        player.setCurrentCellPosition(10);  // Set an initial position

        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));
        when(startCell.getType()).thenReturn(CellType.TELEPORT);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);

        // Act
        gameService.makeChoice(true, "UUID");

        // Assert
        assertEquals(12, player.getCurrentCellPosition());  // Player position should be increased by 2
        verify(lobbyRepository).save(lobby);
        verify(playerRepository).save(player);
    }

    @Test
    void testMakeChoice_TeleportCell_false() {
        // Arrange
        Cell startCell = mock(Cell.class);
        player.setCurrentCellPosition(10);  // Set an initial position

        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));
        when(startCell.getType()).thenReturn(CellType.TELEPORT);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);

        // Act
        gameService.makeChoice(false, "UUID");

        // Assert
        assertEquals(10, player.getCurrentCellPosition());  // Player position should not change
        verify(lobbyRepository).save(lobby);
        verify(playerRepository).save(player);
    }


    @ParameterizedTest
    @MethodSource("testMakeChoiceCAREER_Input")
    void testMakeChoice_Career(boolean chooseLeft, CareerCard card){

        lobby.setCareerCards(List.of(careerCard1, careerCard2));
        Cell startCell = mock(Cell.class);


        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));
        when(startCell.getType()).thenReturn(CellType.CAREER);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);

        gameService.makeChoice(chooseLeft, "UUID");
        assertEquals(card, player.getCareerCard());
    }


    static Stream<Arguments> testMakeChoiceCAREER_Input() {
        return Stream.of(
                Arguments.of(true, careerCard1),
                Arguments.of(false, careerCard2)
        );
    }

    @Test
    void testHandleTurn_CASH(){
        Cell cell = mock(Cell.class);
        when(cellRepository.findByNumber(anyInt())).thenReturn(cell);
        when(cell.getNextCells()).thenReturn(List.of(1));
        when(cell.getType()).thenReturn(CellType.CASH);

        player.setLobbyID(2L);

        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(1);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));

        player.setCareerCard(new CareerCard("Career3", "Career",100, 0, false));

        gameService.handleTurn(player.getPlayerUUID());

        assertEquals(250000+100, player.getMoney());
        verify(lobbyMock).nextPlayer();
    }

    @Test
    void testHandleTurn_CASH_bonusSalary(){
        Cell cell = mock(Cell.class);
        when(cellRepository.findByNumber(anyInt())).thenReturn(cell);
        when(cell.getNextCells()).thenReturn(List.of(1));
        when(cell.getType()).thenReturn(CellType.CASH);

        player.setLobbyID(2L);

        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));

        player.setCareerCard(new CareerCard("Career4", "Career",0, 100, false));

        gameService.handleTurn(player.getPlayerUUID());

        assertEquals(250000+100, player.getMoney());
        verify(lobbyMock).nextPlayer();
    }

    @Test
    void testHandleTurn_ACTION(){
        Cell cell = mock(Cell.class);
        when(cellRepository.findByNumber(anyInt())).thenReturn(cell);
        when(cell.getNextCells()).thenReturn(List.of(1));
        when(cell.getType()).thenReturn(CellType.ACTION);

        player.setLobbyID(2L);

        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));

        ActionCard actionCard = mock(ActionCard.class);
        when(actionCardRepository.findRandomActionCard()).thenReturn(actionCard);

        gameService.handleTurn(player.getPlayerUUID());

        verify(lobbyMock).nextPlayer();
    }

    @Test
    void testHandleTurn_FAMILY(){
        Cell cell = mock(Cell.class);
        when(cellRepository.findByNumber(anyInt())).thenReturn(cell);
        when(cell.getNextCells()).thenReturn(List.of(1));
        when(cell.getType()).thenReturn(CellType.FAMILY);

        player.setLobbyID(2L);
        player.setNumberOfPegs(1);

        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));

        gameService.handleTurn(player.getPlayerUUID());

        assertEquals(2, player.getNumberOfPegs());
        verify(lobbyMock).nextPlayer();
    }

    @Test
    void testHandleTurn_HOUSE(){
        Cell cell = mock(Cell.class);
        when(cellRepository.findByNumber(anyInt())).thenReturn(cell);
        when(cell.getNextCells()).thenReturn(List.of(1));
        when(cell.getType()).thenReturn(CellType.HOUSE);

        player.setLobbyID(2L);
        player.setNumberOfPegs(1);

        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));

        HouseCard houseCard1mock = mock(HouseCard.class);
        HouseCard houseCard2mock = mock(HouseCard.class);
        when(houseCardRepository.searchAffordableHousesForPlayer(player.getMoney())).thenReturn(List.of(houseCard1mock, houseCard2mock));

        gameService.handleTurn(player.getPlayerUUID());

        verify(lobbyMock).setHouseCards(List.of(houseCard1mock, houseCard2mock));
        verify(lobbyMock,(times(2))).setHasDecision(true);
    }

    @Test
    void testHandleTurn_CAREER_College(){
        Cell cell = mock(Cell.class);
        when(cellRepository.findByNumber(anyInt())).thenReturn(cell);
        when(cell.getNextCells()).thenReturn(List.of(1));
        when(cell.getType()).thenReturn(CellType.CAREER);

        player.setLobbyID(2L);
        player.setNumberOfPegs(1);
        player.setCollegeDegree(true);

        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));

        CareerCard careerCard1mock = mock(CareerCard.class);
        when(careerCardRepository.findRandomCareerCard()).thenReturn(careerCard1mock);

        gameService.handleTurn(player.getPlayerUUID());

        verify(lobbyMock).setCareerCards(List.of(careerCard1mock, careerCard1mock));
        verify(lobbyMock,(times(2))).setHasDecision(true);
    }

    @Test
    void testHandleTurn_CAREER_noCollege(){
        Cell cell = mock(Cell.class);
        when(cellRepository.findByNumber(anyInt())).thenReturn(cell);
        when(cell.getNextCells()).thenReturn(List.of(1));
        when(cell.getType()).thenReturn(CellType.CAREER);

        player.setLobbyID(2L);
        player.setNumberOfPegs(1);
        player.setCollegeDegree(false);

        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));

        CareerCard careerCard1mock = mock(CareerCard.class);
        when(careerCard1mock.needsDiploma()).thenReturn(false);

        CareerCard careerCard2mock = mock(CareerCard.class);
        when(careerCard2mock.needsDiploma()).thenReturn(true);

        when(careerCardRepository.findRandomCareerCard()).thenReturn(careerCard1mock, careerCard2mock, careerCard1mock);

        gameService.handleTurn(player.getPlayerUUID());

        verify(lobbyMock).setCareerCards(List.of(careerCard1mock, careerCard1mock));
        verify(lobbyMock,(times(2))).setHasDecision(true);
        verify(careerCard1mock, times(2)).needsDiploma();
    }

    @Test
    void testHandleTurn_MID_LIFE(){
        Cell cell = mock(Cell.class);
        when(cellRepository.findByNumber(anyInt())).thenReturn(cell);
        when(cell.getNextCells()).thenReturn(List.of(1, 2));
        when(cell.getType()).thenReturn(CellType.MID_LIFE);

        player.setLobbyID(2L);
        player.setNumberOfPegs(1);
        player.setCollegeDegree(false);

        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));

        gameService.handleTurn(player.getPlayerUUID());

        verify(lobbyMock).nextPlayer();
    }

    @ParameterizedTest
    @EnumSource(value = CellType.class, names = { "MARRY", "GROW_FAMILY", "RETIRE_EARLY" })
    void testHandleTurn_MARRY_GROWFAMILY_RETIREEARLY(CellType cellType){
        Cell cell = mock(Cell.class);
        when(cellRepository.findByNumber(anyInt())).thenReturn(cell);
        when(cell.getNextCells()).thenReturn(List.of(1));
        when(cell.getType()).thenReturn(cellType);

        player.setLobbyID(2L);
        player.setNumberOfPegs(1);


        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));

        gameService.handleTurn(player.getPlayerUUID());

        verify(lobbyMock, times(0)).nextPlayer();
        verify(lobbyMock).setHasDecision(true);
    }

    @Test
    void testHandleTurn_RETIREMENT_two_players_retired(){
        Cell cell = mock(Cell.class);
        when(cellRepository.findByNumber(anyInt())).thenReturn(cell);
        when(cell.getNextCells()).thenReturn(List.of(1));
        when(cell.getType()).thenReturn(CellType.RETIREMENT);
        Player player2 = new Player("uuid2", "player2");

        player.setLobbyID(2L);

        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyMock.getPlayers()).thenReturn(List.of(player, player2));
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));

        player.setHouses(List.of(new HouseCard("House69","House", 100, 100, 100)));
        player.setNumberOfPegs(1);
        player.setMoney(0);


        gameService.handleTurn(player.getPlayerUUID());

        assertEquals(100 + 50000 + 100000, player.getMoney());
    }

    @Test
    void testHandleTurn_RETIREMENT_four_players_retired(){
        Cell cell = mock(Cell.class);
        when(cellRepository.findByNumber(anyInt())).thenReturn(cell);
        when(cell.getNextCells()).thenReturn(List.of(1));
        when(cell.getType()).thenReturn(CellType.RETIREMENT);
        Player player2 = new Player("uuid2", "player2");
        Player player3 = new Player("uuid3", "player3");
        Player player4 = new Player("uuid4", "player3");

        player.setLobbyID(2L);

        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyMock.getPlayers()).thenReturn(List.of(player, player2, player3, player4));
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));

        player.setHouses(List.of(new HouseCard("House69","House", 100, 100, 100)));
        player.setNumberOfPegs(1);
        player.setMoney(0);


        gameService.handleTurn(player.getPlayerUUID());

        assertEquals(100 + 50000 + 10000, player.getMoney());
    }

    @Test
    void testDoAction_AffectOnePlayer() {
        ActionCard actionCard = mock(ActionCard.class);
        when(actionCard.isAffectOnePlayer()).thenReturn(true);
        when(actionCard.getMoneyAmount()).thenReturn(100);

        gameService.doAction(player, actionCard);

        assertEquals(250100, player.getMoney());
    }

    @Test
    void testDoAction_AffectAllPlayers() {
        List<Player> players = List.of(player, new Player("UUID2", "player2"), new Player("UUID3", "player3"));
        ActionCard actionCard = mock(ActionCard.class);
        when(actionCard.isAffectAllPlayers()).thenReturn(true);
        when(actionCard.getMoneyAmount()).thenReturn(100);
        when(playerRepository.findAll()).thenReturn(players);

        gameService.doAction(player, actionCard);

        players.forEach(p -> assertEquals(250100, p.getMoney()));
    }

    @Test
    void testDoAction_AffectBank() {
        ActionCard actionCard = mock(ActionCard.class);
        when(actionCard.isAffectBank()).thenReturn(true);
        when(actionCard.getMoneyAmount()).thenReturn(100);

        gameService.doAction(player, actionCard);

        assertEquals(250100, player.getMoney());
    }


    @Test
    void testEndGameEarlier_onePlayer(){
        Cell retirement = new Cell(123, CellType.RETIREMENT, null, 1, 2);

        when(cellRepository.findByNumber(123)).thenReturn(retirement);

        player.setLobbyID(2L);
        player.setCurrentCellPosition(10);

        Lobby lobbyMock = mock(Lobby.class);
        when(lobbyMock.getLobbyID()).thenReturn(2L);
        when(lobbyMock.getCurrentPlayer()).thenReturn(player);
        when(lobbyMock.getSpunNumber()).thenReturn(2);
        when(lobbyMock.getPlayers()).thenReturn(List.of(player));
        when(lobbyRepository.findById(2L)).thenReturn(Optional.of(lobbyMock));
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));

        player.setHouses(List.of(new HouseCard("House69","House", 100, 100, 100)));
        player.setNumberOfPegs(1);
        player.setMoney(0);


        gameService.endGameEarlier(player.getPlayerUUID());

        assertEquals(100 + 50000 + 200000, player.getMoney());
        verify(lobbyMock).setHasStarted(false);
    }


    @Test
    void testEndGameEarlier_twoPlayer(){
        Cell retirement = new Cell(123, CellType.RETIREMENT, null, 1, 2);
        Cell player1Cell = new Cell(10, CellType.NOTHING, null, 1, 2);


        when(cellRepository.findByNumber(123)).thenReturn(retirement);
        when(cellRepository.findByNumber(10)).thenReturn(player1Cell);

        player.setCurrentCellPosition(20);


        Player player2 = new Player("1234", "Player2");
        player2.setLobbyID(1L);
        player2.setCurrentCellPosition(10);


        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(1L)).thenReturn(Optional.of(lobby));

        lobby.setPlayers(List.of(player, player2));

        player.setHouses(List.of(new HouseCard("House69", "House", 100, 100, 100)));
        player.setNumberOfPegs(1);
        player.setMoney(0);

        player2.setHouses(List.of(new HouseCard("House69", "House", 100, 100, 100)));
        player2.setNumberOfPegs(1);
        player2.setMoney(0);

        gameService.endGameEarlier(player.getPlayerUUID());

        assertEquals(100 + 50000 + 200000, player.getMoney());
        assertEquals(100 + 50000 + 100000, player2.getMoney());
    }

    @AfterEach
    void breakDown() {
        this.playerRepository = null;
        this.lobbyRepository = null;
        this.cellRepository = null;
        this.gameService = null;
        this.player = null;
        this.lobby = null;
    }
}
