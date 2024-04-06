package se2.group3.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import se2.group3.backend.dto.LobbyDTO;
import se2.group3.backend.dto.PlayerDTO;
import se2.group3.backend.exceptions.SessionOperationException;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class LobbyServiceTests {

    @InjectMocks
    private LobbyService lobbyService;

    @BeforeEach
    void setUp() {
        lobbyService = new LobbyService();
    }

    @Test
    void createLobby() {
        PlayerDTO playerDTO = Mockito.mock(PlayerDTO.class);
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);

        Mockito.when(playerDTO.playerName()).thenReturn("Player 1");
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(Map.of());

        try {
            LobbyDTO lobby = lobbyService.createLobby(playerDTO, headerAccessor);
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(0, lobby.lobbyID());
            Assertions.assertEquals("Player 1", lobby.host().getPlayerName());
        } catch (SessionOperationException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void createLobbyWhileInLobby() {
        PlayerDTO host = Mockito.mock(PlayerDTO.class);
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);

        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(Map.of("lobbyID", 12L));
        try {
            lobbyService.createLobby(host, headerAccessor);
        } catch (IllegalStateException e) {
            Assertions.assertEquals(IllegalStateException.class, e.getClass());
        } catch (SessionOperationException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void joinLobby() {
        PlayerDTO host = Mockito.mock(PlayerDTO.class);
        SimpMessageHeaderAccessor headerAccessorHost = Mockito.mock(SimpMessageHeaderAccessor.class);
        PlayerDTO player = Mockito.mock(PlayerDTO.class);
        SimpMessageHeaderAccessor headerAccessorPlayer = Mockito.mock(SimpMessageHeaderAccessor.class);

        Mockito.when(headerAccessorHost.getSessionAttributes()).thenReturn(Map.of());
        Mockito.when(host.playerName()).thenReturn("Player 1");
        Mockito.when(headerAccessorPlayer.getSessionAttributes()).thenReturn(new HashMap<>());
        Mockito.when(player.playerName()).thenReturn("Player 2");

        try {
            lobbyService.createLobby(host, headerAccessorHost);
        } catch (Exception e) {
            Assertions.fail(e);
        }

        try {
            LobbyDTO lobby = lobbyService.joinLobby(0L, player, headerAccessorPlayer);
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(0, lobby.lobbyID());
            Assertions.assertEquals("Player 1", lobby.host().getPlayerName());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void joinLobbyFull() {
        PlayerDTO host = Mockito.mock(PlayerDTO.class);
        SimpMessageHeaderAccessor headerAccessorHost = Mockito.mock(SimpMessageHeaderAccessor.class);
        PlayerDTO player1 = Mockito.mock(PlayerDTO.class);
        PlayerDTO player2 = Mockito.mock(PlayerDTO.class);
        PlayerDTO player3 = Mockito.mock(PlayerDTO.class);
        SimpMessageHeaderAccessor headerAccessorPlayer1 = Mockito.mock(SimpMessageHeaderAccessor.class);
        SimpMessageHeaderAccessor headerAccessorPlayer2 = Mockito.mock(SimpMessageHeaderAccessor.class);
        SimpMessageHeaderAccessor headerAccessorPlayer3 = Mockito.mock(SimpMessageHeaderAccessor.class);

        Mockito.when(headerAccessorHost.getSessionAttributes()).thenReturn(Map.of());
        Mockito.when(host.playerName()).thenReturn("Host");
        Mockito.when(headerAccessorPlayer1.getSessionAttributes()).thenReturn(new HashMap<>());
        Mockito.when(headerAccessorPlayer2.getSessionAttributes()).thenReturn(new HashMap<>());
        Mockito.when(headerAccessorPlayer3.getSessionAttributes()).thenReturn(new HashMap<>());

        try {
            lobbyService.createLobby(host, headerAccessorHost);
        } catch (Exception e) {
            Assertions.fail(e);
        }

        try {
            LobbyDTO lobby = lobbyService.joinLobby(0L, player1, headerAccessorPlayer1);
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(0, lobby.lobbyID());
            Assertions.assertEquals("Host", lobby.host().getPlayerName());
        } catch (Exception e) {
            Assertions.fail(e);
        }
        try {
            LobbyDTO lobby = lobbyService.joinLobby(0L, player2, headerAccessorPlayer2);
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(0, lobby.lobbyID());
            Assertions.assertEquals("Host", lobby.host().getPlayerName());
        } catch (Exception e) {
            Assertions.fail(e);
        }

        try {
            lobbyService.joinLobby(0L, player3, headerAccessorPlayer3);
        } catch (Exception e) {
            Assertions.assertEquals(IllegalStateException.class, e.getClass());
        }
    }

    @Test
    void joinLobbyNotFound() {
        PlayerDTO playerDTO = Mockito.mock(PlayerDTO.class);
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);

        try {
            lobbyService.joinLobby(1L, playerDTO, headerAccessor);
        } catch (Exception e) {
            Assertions.assertEquals(IllegalStateException.class, e.getClass());
        }
    }

    @Test
    void joinLobbyWhileInLobby() {
        PlayerDTO host = Mockito.mock(PlayerDTO.class);
        SimpMessageHeaderAccessor headerAccessorHost = Mockito.mock(SimpMessageHeaderAccessor.class);
        PlayerDTO playerDTO = Mockito.mock(PlayerDTO.class);
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);

        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(Map.of("lobbyID", 2L));

        try {
            lobbyService.createLobby(host, headerAccessorHost);
        } catch (Exception e) {
            Assertions.fail(e);
        }

        try {
            lobbyService.joinLobby(0L, playerDTO, headerAccessor);
        } catch (Exception e) {
            Assertions.assertEquals(IllegalStateException.class, e.getClass());
        }
    }

    @Test
    void leaveLobby() {
        PlayerDTO playerDTO = Mockito.mock(PlayerDTO.class);
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);

        Map<String, Object> headerMap = new HashMap<>();
        Mockito.when(playerDTO.playerName()).thenReturn("Player 1");
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(headerMap);
        Mockito.when(headerAccessor.getSessionId()).thenReturn("ABC123");

        try {
            LobbyDTO lobby = lobbyService.createLobby(playerDTO, headerAccessor);
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(0, lobby.lobbyID());
            Assertions.assertEquals("Player 1", lobby.host().getPlayerName());
        } catch (SessionOperationException e) {
            Assertions.fail(e);
        }

        try {
            LobbyDTO lobbyDTO = lobbyService.leaveLobby(headerAccessor);
            Assertions.assertEquals(0, lobbyDTO.lobbyID());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void leaveLobbyWhileNotInLobby() {
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);
    }

    @Test
    void leaveLobbyNotFound() {
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);
    }

}