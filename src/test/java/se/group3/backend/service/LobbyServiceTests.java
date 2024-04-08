package se.group3.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.exceptions.SessionOperationException;
import se.group3.backend.services.LobbyService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

        Map<String, Object> header = new HashMap<>();
        Mockito.when(playerDTO.getPlayerName()).thenReturn("Player 1");
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(header);

        try {
            LobbyDTO lobby = lobbyService.createLobby(playerDTO, headerAccessor);
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(0, lobby.getLobbyID());
            Assertions.assertEquals("Player 1", lobby.getHost().getPlayerName());
        } catch (SessionOperationException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void createLobbyWhileInLobby() {
        PlayerDTO host = Mockito.mock(PlayerDTO.class);
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);
        Map<String, Object> header = new HashMap<>();
        header.put("lobbyID", 12L);
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(header);
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

        Mockito.when(headerAccessorHost.getSessionAttributes()).thenReturn(new HashMap<>());
        Mockito.when(host.getPlayerName()).thenReturn("Player 1");
        Mockito.when(headerAccessorPlayer.getSessionAttributes()).thenReturn(new HashMap<>());
        Mockito.when(player.getPlayerName()).thenReturn("Player 2");

        try {
            lobbyService.createLobby(host, headerAccessorHost);
        } catch (Exception e) {
            Assertions.fail(e);
        }

        try {
            LobbyDTO lobby = lobbyService.joinLobby(0L, player, headerAccessorPlayer);
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(0, lobby.getLobbyID());
            Assertions.assertEquals("Player 1", lobby.getHost().getPlayerName());
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

        Mockito.when(headerAccessorHost.getSessionAttributes()).thenReturn(new HashMap<>());
        Mockito.when(host.getPlayerName()).thenReturn("Host");
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
            Assertions.assertEquals(0, lobby.getLobbyID());
            Assertions.assertEquals("Host", lobby.getHost().getPlayerName());
        } catch (Exception e) {
            Assertions.fail(e);
        }
        try {
            LobbyDTO lobby = lobbyService.joinLobby(0L, player2, headerAccessorPlayer2);
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(0, lobby.getLobbyID());
            Assertions.assertEquals("Host", lobby.getHost().getPlayerName());
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
        headerMap.put("uuid", "1234");
        Mockito.when(playerDTO.getPlayerName()).thenReturn("Player 1");
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(headerMap);

        try {
            LobbyDTO lobby = lobbyService.createLobby(playerDTO, headerAccessor);
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(0, lobby.getLobbyID());
            Assertions.assertEquals("Player 1", lobby.getHost().getPlayerName());
        } catch (SessionOperationException e) {
            Assertions.fail(e);
        }

        try {
            LobbyDTO lobbyDTO = lobbyService.leaveLobby(headerAccessor);
            Assertions.assertEquals(0, lobbyDTO.getLobbyID());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void leaveLobbyWhileNotInLobby() {
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("uuid", "1234");
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(headerMap);

        Exception ex = assertThrows(IllegalStateException.class, () -> lobbyService.leaveLobby(headerAccessor));
        Assertions.assertEquals("Attempting to leave lobby, when player is not part of any lobby!", ex.getMessage());
    }

    @Test
    void leaveLobbyNotFound() {
        SimpMessageHeaderAccessor headerAccessor = Mockito.mock(SimpMessageHeaderAccessor.class);

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("lobbyID", 12L);
        headerMap.put("uuid", "1234");
        Mockito.when(headerAccessor.getSessionAttributes()).thenReturn(headerMap);

        Exception ex = assertThrows(IllegalStateException.class, () -> lobbyService.leaveLobby(headerAccessor));
        Assertions.assertEquals("Lobby associated with session connection does not exist!", ex.getMessage());
    }

}