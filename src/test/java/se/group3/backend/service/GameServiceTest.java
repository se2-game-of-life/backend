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
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;

import se.group3.backend.repositories.CellRepository;
import se.group3.backend.repositories.LobbyRepository;
import se.group3.backend.repositories.PlayerRepository;
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
    private Player player;
    private Lobby lobby;

    private final HouseCard houseCard1 = new HouseCard("House1", 50000,0,0);
    private final HouseCard houseCard2 = new HouseCard("House2", 10000,0,0);
    private final static CareerCard careerCard1 = new CareerCard("Career1", 100, 200, false);
    private final static CareerCard careerCard2 = new CareerCard("Career2", 100, 200, false);


    @BeforeEach
    void setUp() {
        this.playerRepository = mock(PlayerRepository.class);
        this.lobbyRepository = mock(LobbyRepository.class);
        this.cellRepository = mock(CellRepository.class);
        this.gameService = new GameService(null, null, null, cellRepository, playerRepository, lobbyRepository);
        player = new Player();
        player.setCurrentCellPosition(0);
        player.setMoney(250000);
        player.setLobbyID(1L);
        player.setPlayerUUID("UUID");
        lobby = new Lobby(1L, player);
        lobby.setCurrentPlayer(player);
    }



    @Test
    void testCareerOrCollegeChoice_CollegePath(){
        assertFalse(player.isCollegeDegree());


        Cell startCell = mock(Cell.class);
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));

        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(startCell.getType()).thenReturn(CellType.START);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);


        gameService.makeChoice(true, "UUID");
        assertEquals(150000, player.getMoney());
        assertTrue(player.isCollegeDegree());
    }

    @Test
    void testCareerOrCollegeChoice_CareerPath(){
        assertFalse(player.isCollegeDegree());


        Cell startCell = mock(Cell.class);

        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));
        when(startCell.getType()).thenReturn(CellType.START);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);


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
    void testMakeChoice_Houses_true(){

        lobby.setCards(List.of(houseCard1, houseCard2));
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
        lobby.setCards(List.of(houseCard1, houseCard2));
        Cell startCell = mock(Cell.class);


        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));
        when(startCell.getType()).thenReturn(CellType.HOUSE);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);


        gameService.makeChoice(false, "UUID");
        assertEquals(240000, player.getMoney());
        assertEquals(List.of(houseCard2), player.getHouses());
    }

    @ParameterizedTest
    @MethodSource("testMakeChoiceCAREER_Input")
    void testMakeChoice_Career(boolean chooseLeft, CareerCard card){

        lobby.setCards(List.of(careerCard1, careerCard2));
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
