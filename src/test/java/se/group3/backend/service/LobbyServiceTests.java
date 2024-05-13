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
import se.group3.backend.repositories.LobbyRepository;
import se.group3.backend.repositories.PlayerRepository;
import se.group3.backend.services.LobbyService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LobbyServiceTests {

    @InjectMocks
    private final LobbyRepository lobbyRepository = Mockito.mock(LobbyRepository.class);

    @InjectMocks
    private final PlayerRepository playerRepository = Mockito.mock(PlayerRepository.class);

    @InjectMocks
    private LobbyService lobbyService;

    @BeforeEach
    void setUp() {
        lobbyService = new LobbyService(lobbyRepository, playerRepository);
    }

    @Test
    void createLobby() {
        PlayerDTO playerDTO = Mockito.mock(PlayerDTO.class);

        Mockito.when(playerDTO.getPlayerName()).thenReturn("/Player 1/");

        try {
            LobbyDTO lobby = lobbyService.createLobby(playerDTO.getPlayerUUID(), playerDTO.getPlayerName());
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(1, lobby.getLobbyID());
            Assertions.assertEquals("Player 1", lobby.getPlayers().get(0).getPlayerName());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

//    @Test
//    void createLobbyWhileInLobby() {
//        PlayerDTO dto = Mockito.mock(PlayerDTO.class);
//        try {
//            lobbyService.createLobby(dto.getPlayerUUID(), dto.getPlayerName());
//        } catch (IllegalStateException e) {
//            Assertions.assertEquals(IllegalStateException.class, e.getClass());
//        } catch (Exception e) {
//            Assertions.fail(e);
//        }
//    }

   /* @Test
    void joinLobby() {
        PlayerDTO dto = Mockito.mock(PlayerDTO.class);
        PlayerDTO player = Mockito.mock(PlayerDTO.class);

        Mockito.when(dto.getPlayerName()).thenReturn("Player 1");
        Mockito.when(player.getPlayerName()).thenReturn("Player 2");

        try {
            lobbyService.createLobby(dto.getPlayerUUID(), dto.getPlayerName());
        } catch (Exception e) {
            Assertions.fail(e);
        }

        try {
            LobbyDTO lobby = lobbyService.joinLobby(0L, player.getPlayerUUID(), player.getPlayerName());
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(0, lobby.getLobbyID());
            Assertions.assertEquals("Player 1",lobby.getCurrentPlayer().getPlayerName());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void joinLobbyFull() {
        PlayerDTO dto = Mockito.mock(PlayerDTO.class);
        PlayerDTO player1 = Mockito.mock(PlayerDTO.class);
        PlayerDTO player2 = Mockito.mock(PlayerDTO.class);
        PlayerDTO player3 = Mockito.mock(PlayerDTO.class);

        Mockito.when(dto.getPlayerName()).thenReturn("Host");

        try {
            lobbyService.createLobby(dto.getPlayerUUID(), dto.getPlayerName());
        } catch (Exception e) {
            Assertions.fail(e);
        }

        try {
            LobbyDTO lobby = lobbyService.joinLobby(0L, player1.getPlayerUUID(), player1.getPlayerName());
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(0, lobby.getLobbyID());
        } catch (Exception e) {
            Assertions.fail(e);
        }
        try {
            LobbyDTO lobby = lobbyService.joinLobby(0L, player2.getPlayerUUID(), player2.getPlayerName());
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(0, lobby.getLobbyID());
        } catch (Exception e) {
            Assertions.fail(e);
        }

        try {
            lobbyService.joinLobby(0L, player3.getPlayerUUID(), player3.getPlayerName());
        } catch (Exception e) {
            Assertions.assertEquals(IllegalStateException.class, e.getClass());
        }
    }*/

    @Test
    void joinLobbyNotFound() {
        PlayerDTO playerDTO = Mockito.mock(PlayerDTO.class);

        try {
            lobbyService.joinLobby(1L, playerDTO.getPlayerUUID(), playerDTO.getPlayerName());
        } catch (Exception e) {
            Assertions.assertEquals(IllegalStateException.class, e.getClass());
        }
    }

    @Test
    void joinLobbyWhileInLobby() {
        PlayerDTO dto = Mockito.mock(PlayerDTO.class);
        PlayerDTO playerDTO = Mockito.mock(PlayerDTO.class);

        try {
            lobbyService.createLobby(dto.getPlayerUUID(), playerDTO.getPlayerName());
        } catch (Exception e) {
            Assertions.fail(e);
        }

        try {
            lobbyService.joinLobby(0L, dto.getPlayerUUID(), dto.getPlayerName());
        } catch (Exception e) {
            Assertions.assertEquals(IllegalStateException.class, e.getClass());
        }
    }

  /*  @Test
    void leaveLobby() {
        PlayerDTO dto = Mockito.mock(PlayerDTO.class);

        Mockito.when(dto.getPlayerName()).thenReturn("Player 1");

        try {
            LobbyDTO lobby = lobbyService.createLobby(dto.getPlayerUUID(), dto.getPlayerName());
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(1, lobby.getLobbyID());
            Assertions.assertEquals("Player 1", lobby.getCurrentPlayer().getPlayerName());
        } catch (Exception e) {
            Assertions.fail(e);
        }

        try {
            LobbyDTO lobbyDTO = lobbyService.leaveLobby(dto.getPlayerUUID());
            Assertions.assertEquals(0, lobbyDTO.getLobbyID());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
*/

}