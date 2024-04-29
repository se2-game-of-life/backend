package se.group3.backend.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.group3.backend.domain.cells.ActionCell;
import se.group3.backend.domain.cells.Cell;
import se.group3.backend.domain.cells.StopCell;
import se.group3.backend.domain.player.Player;
import se.group3.backend.domain.player.PlayerStatistic;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.dto.mapper.PlayerMapper;
import se.group3.backend.repositories.player.PlayerRepository;
import se.group3.backend.services.GameServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {
    private PlayerDTO playerDTOMockHost;
    private PlayerDTO playerDTOMock;
    private PlayerDTO[] playersMock = new PlayerDTO[2];
    private LobbyDTO lobbyDTOMock;
    private GameServiceImpl gameService;

    @Mock
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp(){
        this.playerDTOMockHost = mock(PlayerDTO.class);
        this.playerDTOMock = mock(PlayerDTO.class);
        this.lobbyDTOMock = mock(LobbyDTO.class);
        this.gameService = new GameServiceImpl(null, null, null, null, playerRepository);
    }


    @Test
    void testPlayerStats(){
        playersMock[0] = playerDTOMockHost;
        playersMock[1] = playerDTOMock;
        Player player1 = new Player("Player1");
        player1.setMoney(150);
        player1.setInvestmentNumber(1);
        player1.setNumberOfPegs(2);

        when(lobbyDTOMock.getPlayers()).thenReturn(playersMock);
        when(playerDTOMock.getPlayerName()).thenReturn("Player1");
        when(playerDTOMock.getMoney()).thenReturn(150);
        when(playerDTOMock.getInvestmentNumber()).thenReturn(1);
        when(playerDTOMock.getNumberOfPegs()).thenReturn(2);

        PlayerStatistic expectedStatistics = new PlayerStatistic(player1);
        List <PlayerStatistic> actualStatistics = gameService.getPlayerStats(playerDTOMockHost, lobbyDTOMock);
        assertTrue(actualStatistics.contains(expectedStatistics));
        verify(lobbyDTOMock, times(1)).getPlayers();
        verify(playerDTOMock, times(1)).getMoney();
        verify(playerDTOMock, times(1)).getInvestmentNumber();
        verify(playerDTOMock, times(1)).getNumberOfPegs();
        verify(playerDTOMock, times(1)).getPlayerName();
    }

    private String playerName;
    private int money;
    private int investmentNumber;
    private int numberOfPegs;

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

    @Test
    void testHandleMove() {
        // Arrange
        PlayerDTO playerDTO = mock(PlayerDTO.class);
        when(playerDTO.getPlayerID()).thenReturn("player1");
        Player player = new Player("player1");

        ActionCell actionCell = mock(ActionCell.class);
        StopCell stopCell = mock(StopCell.class);
        Cell genericCell = mock(Cell.class);

        List<Cell> cells = Arrays.asList(genericCell, actionCell, stopCell);

        // Setup repository to return player
        when(playerRepository.findById("player1")).thenReturn(Optional.of(player));

        // Act
        gameService.handleMove(playerDTO, cells);

        // Assert
        InOrder inOrder = inOrder(genericCell, actionCell, stopCell);
        inOrder.verify(genericCell).performAction(player);
    }

    @Test
    void handleMove_StopsAtStopCell() {
        // Arrange
        PlayerDTO playerDTO = mock(PlayerDTO.class);
        when(playerDTO.getPlayerID()).thenReturn("player1");
        Player player = new Player("player1");

        ActionCell actionCell = mock(ActionCell.class);
        StopCell stopCell = mock(StopCell.class);
        Cell genericCell = mock(Cell.class);

        List<Cell> cells = Arrays.asList(genericCell, stopCell, actionCell);  // StopCell in the middle

        when(playerRepository.findById("player1")).thenReturn(Optional.of(player));

        // Act
        gameService.handleMove(playerDTO, cells);

        // Assert
        verify(genericCell).performAction(player);

    }

    @Test
    void handleMove_LastCellActionCell_PerformsAction() {
        // Arrange
        PlayerDTO playerDTO = mock(PlayerDTO.class);
        when(playerDTO.getPlayerID()).thenReturn("player1");
        Player player = new Player("player1");

        ActionCell lastActionCell = mock(ActionCell.class);
        Cell firstGenericCell = mock(Cell.class);
        Cell secondGenericCell = mock(Cell.class);

        List<Cell> cells = Arrays.asList(firstGenericCell, secondGenericCell, lastActionCell);

        when(playerRepository.findById("player1")).thenReturn(Optional.of(player));

        // Act
        gameService.handleMove(playerDTO, cells);

        // Assert
        verify(firstGenericCell).performAction(player);
    }






    @AfterEach
    void breakDown(){
        this.playerDTOMock = null;
        this.lobbyDTOMock = null;
    }
}