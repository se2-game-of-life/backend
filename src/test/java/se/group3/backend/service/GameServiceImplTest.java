package se.group3.backend.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.group3.backend.domain.player.Player;
import se.group3.backend.domain.player.PlayerStatistic;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.dto.mapper.PlayerMapper;
import se.group3.backend.services.GameServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {
    private PlayerDTO playerDTOMockHost;
    private PlayerDTO playerDTOMock;
    private PlayerDTO[] playersMock = new PlayerDTO[2];
    private LobbyDTO lobbyDTOMock;
    private GameServiceImpl gameService;


    @BeforeEach
    void setUp(){
        this.playerDTOMockHost = mock(PlayerDTO.class);
        this.playerDTOMock = mock(PlayerDTO.class);
        this.lobbyDTOMock = mock(LobbyDTO.class);
        this.gameService = new GameServiceImpl();
    }


    @Test
    void testPlayerStats(){
        playersMock[0] = playerDTOMockHost;
        playersMock[1] = playerDTOMock;
        Player player1 = new Player("Player1");
        player1.setMoney(150);
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

    @AfterEach
    void breakDown(){
        this.playerDTOMock = null;
        this.lobbyDTOMock = null;
    }
}
