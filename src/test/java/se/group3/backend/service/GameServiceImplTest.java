package se.group3.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import se.group3.backend.domain.Player;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.repositories.PlayerRepository;
import se.group3.backend.services.GameService;
import se.group3.backend.services.SerializationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {
    private PlayerDTO playerDTOMock;
    private final List<PlayerDTO> playersMock = new ArrayList<>(2);
    private LobbyDTO lobbyDTOMock;
    private GameService gameService;

    @Mock
    private PlayerRepository playerRepository;


    @BeforeEach
    void setUp() {
        this.playerDTOMock = mock(PlayerDTO.class);
        this.lobbyDTOMock = mock(LobbyDTO.class);
        this.gameService = new GameService(null, null, null, null, playerRepository, null);
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


    @AfterEach
    void breakDown() {
        this.playerDTOMock = null;
        this.lobbyDTOMock = null;
        this.gameService = null;
    }
}
