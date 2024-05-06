package se.group3.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.group3.backend.domain.player.Player;
import se.group3.backend.domain.player.PlayerStatistic;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.dto.mapper.PlayerMapper;
import se.group3.backend.repositories.player.PlayerRepository;
import se.group3.backend.services.GameServiceImpl;
import se.group3.backend.util.SerializationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {
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
        player1.setNumberOfPegs(2);

        when(lobbyDTOMock.getPlayers()).thenReturn(playersMock);
        when(playerDTOMock.getPlayerName()).thenReturn("Player1");
        when(playerDTOMock.getMoney()).thenReturn(150);
        when(playerDTOMock.getNumberOfPegs()).thenReturn(2);

        PlayerStatistic expectedStatistics = new PlayerStatistic(player1);
        List <PlayerStatistic> actualStatistics = gameService.getPlayerStats(playerDTOMockHost, lobbyDTOMock);
        assertTrue(actualStatistics.contains(expectedStatistics));
        verify(lobbyDTOMock, times(1)).getPlayers();
        verify(playerDTOMock, times(1)).getMoney();
        verify(playerDTOMock, times(1)).getNumberOfPegs();
        verify(playerDTOMock, times(1)).getPlayerName();
    }

    @Test
    void testChoosePath_CollegePath(){
        PlayerDTO playerDTO = new PlayerDTO("player1");
        playerDTO.setCollegePath(true);
        playerDTO.setMoney(250000);
        playerDTO.setPlayerID("1");
        String playerUUID = null;
        Player player = new Player("player1");


        when(playerRepository.findById("1")).thenReturn(Optional.of(player));

        try {
            playerUUID = SerializationUtil.jsonStringFromClass(playerDTO);
        } catch (JsonProcessingException e) {
            Assertions.fail(e);
        }
        playerUUID = gameService.choosePath(playerUUID);
        try {
            playerDTO = (PlayerDTO) SerializationUtil.toObject(playerUUID, PlayerDTO.class);
        } catch (JsonProcessingException e) {
            Assertions.fail(e);
        }

        assertEquals(150000, playerDTO.getMoney());
        assertTrue(playerDTO.isCollegePath());
        assertTrue(player.isCollegeDegree());

    }

    @Test
    void testChoosePath_CareerPath(){
        PlayerDTO playerDTO = new PlayerDTO("player1");
        playerDTO.setCollegePath(false);
        playerDTO.setMoney(250000);
        playerDTO.setPlayerID("1");
        String playerUUID = null;
        Player player = new Player("player1");


        when(playerRepository.findById("1")).thenReturn(Optional.of(player));

        try {
            playerUUID = SerializationUtil.jsonStringFromClass(playerDTO);
        } catch (JsonProcessingException e) {
            Assertions.fail(e);
        }
        playerUUID = gameService.choosePath(playerUUID);
        try {
            playerDTO = (PlayerDTO) SerializationUtil.toObject(playerUUID, PlayerDTO.class);
        } catch (JsonProcessingException e) {
            Assertions.fail(e);
        }

        assertEquals(250000, playerDTO.getMoney());
        assertFalse(playerDTO.isCollegePath());
        assertFalse(player.isCollegeDegree());
    }



    @AfterEach
    void breakDown(){
        this.playerDTOMock = null;
        this.lobbyDTOMock = null;
    }
}
