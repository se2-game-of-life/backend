package se.group3.backend.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.junit.jupiter.MockitoExtension;
import se.group3.backend.domain.Cell;
import se.group3.backend.domain.CellType;
import se.group3.backend.domain.Lobby;
import se.group3.backend.domain.Player;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.repositories.CellRepository;
import se.group3.backend.repositories.LobbyRepository;
import se.group3.backend.repositories.PlayerRepository;
import se.group3.backend.services.GameService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    private PlayerDTO playerDTOMock;
    private final List<PlayerDTO> playersMock = new ArrayList<>(2);
    private LobbyDTO lobbyDTOMock;
    private GameService gameService;
    private PlayerRepository playerRepository;
    private LobbyRepository lobbyRepository;
    private CellRepository cellRepository;


    @BeforeEach
    void setUp() {
        this.playerDTOMock = mock(PlayerDTO.class);
        this.lobbyDTOMock = mock(LobbyDTO.class);
        this.playerRepository = mock(PlayerRepository.class);
        this.lobbyRepository = mock(LobbyRepository.class);
        this.cellRepository = mock(CellRepository.class);
        this.gameService = new GameService(null, null, null, cellRepository, playerRepository, lobbyRepository);
    }


    @Test
    void testPlayerStats() {
        playersMock.add(playerDTOMock);
        playersMock.add(playerDTOMock);
        Player player1 = new Player();
        player1.setPlayerUUID("ID");
        player1.setPlayerName("Player1");
        player1.setMoney(150);
        player1.setNumberOfPegs(2);

        lenient().when(lobbyDTOMock.getPlayers()).thenReturn(playersMock);
        lenient().when(playerDTOMock.getPlayerName()).thenReturn("Player1");
        lenient().when(playerDTOMock.getMoney()).thenReturn(150);
        lenient().when(playerDTOMock.getNumberOfPegs()).thenReturn(2);

        verify(lobbyDTOMock, atMostOnce()).getPlayers();
        verify(playerDTOMock, atMostOnce()).getMoney();
        verify(playerDTOMock, atMostOnce()).getNumberOfPegs();
        verify(playerDTOMock, atMostOnce()).getPlayerName();
    }

    @Test
    void testCareerOrCollegeChoice_CollegePath(){
        Player player = new Player();
        player.setCurrentCellPosition(0);
        player.setMoney(250000);
        assertFalse(player.isCollegeDegree());
        player.setLobbyID(1L);
        player.setPlayerUUID("UUID");
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));

        Lobby lobby = new Lobby(1L, player);
        lobby.setCurrentPlayer(player);
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));

        Cell startCell = mock(Cell.class);
        when(startCell.getType()).thenReturn(CellType.START);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);


        gameService.makeChoice(true, "UUID");
        assertEquals(150000, player.getMoney());
        assertTrue(player.isCollegeDegree());
    }

    @Test
    void testCareerOrCollegeChoice_CareerPath(){
        Player player = new Player();
        player.setCurrentCellPosition(0);
        player.setMoney(250000);
        assertFalse(player.isCollegeDegree());
        player.setLobbyID(1L);
        player.setPlayerUUID("UUID");
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));

        Lobby lobby = new Lobby(1L, player);
        lobby.setCurrentPlayer(player);
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));

        Cell startCell = mock(Cell.class);
        when(startCell.getType()).thenReturn(CellType.START);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);


        gameService.makeChoice(false, "UUID");
        assertEquals(250000, player.getMoney());
        assertFalse(player.isCollegeDegree());
    }

    @ParameterizedTest
    @EnumSource(value = CellType.class, names = { "MARRY", "GROW_FAMILY" })
    void makeChoice_Marry_or_GrowFamily_true(CellType type){
        Player player = new Player();
        player.setMoney(250000);
        assertFalse(player.isCollegeDegree());
        player.setLobbyID(1L);
        player.setNumberOfPegs(0);
        player.setPlayerUUID("UUID");
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));

        Lobby lobby = new Lobby(1L, player);
        lobby.setCurrentPlayer(player);
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));

        Cell startCell = mock(Cell.class);
        when(startCell.getType()).thenReturn(type);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);


        gameService.makeChoice(true, "UUID");
        assertEquals(200000, player.getMoney());
        assertEquals(1, player.getNumberOfPegs());
    }

    @ParameterizedTest
    @EnumSource(value = CellType.class, names = { "MARRY", "GROW_FAMILY" })
    void makeChoice_Marry_or_GrowFamily_false(CellType type){
        Player player = new Player();
        player.setMoney(250000);
        assertFalse(player.isCollegeDegree());
        player.setLobbyID(1L);
        player.setNumberOfPegs(0);
        player.setPlayerUUID("UUID");
        when(playerRepository.findById("UUID")).thenReturn(Optional.of(player));

        Lobby lobby = new Lobby(1L, player);
        lobby.setCurrentPlayer(player);
        when(lobbyRepository.findById(player.getLobbyID())).thenReturn(Optional.of(lobby));

        Cell startCell = mock(Cell.class);
        when(startCell.getType()).thenReturn(type);
        when(cellRepository.findByNumber(player.getCurrentCellPosition())).thenReturn(startCell);


        gameService.makeChoice(false, "UUID");
        assertEquals(250000, player.getMoney());
        assertEquals(0, player.getNumberOfPegs());
    }


    @AfterEach
    void breakDown() {
        this.playerDTOMock = null;
        this.lobbyDTOMock = null;
        this.gameService = null;
    }
}
