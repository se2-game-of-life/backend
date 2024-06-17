package se.group3.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.group3.backend.domain.Lobby;
import se.group3.backend.domain.Player;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.repositories.LobbyRepository;
import se.group3.backend.repositories.PlayerRepository;
import se.group3.backend.services.LobbyService;

import java.util.Optional;


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
        try {
            LobbyDTO lobby = lobbyService.createLobby("Test UUID", "/Player 1/");
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(1, lobby.getLobbyID());
            Assertions.assertEquals("Test UUID", lobby.getCurrentPlayer().getPlayerUUID());
            Assertions.assertEquals("Player 1", lobby.getCurrentPlayer().getPlayerName());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }



    @Test
    void createLobbyWhileInLobby() {
        try {
            lobbyService.createLobby("Test UUID", "/Player 1/");
            lobbyService.createLobby("Test UUID", "/Player 1/");
        } catch (IllegalStateException e) {
            Assertions.assertEquals(IllegalStateException.class, e.getClass());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void joinLobby() {
        Mockito.when(lobbyRepository.findById(1L)).thenReturn(Optional.of(new Lobby(1L, new Player("Test UUID1", "Player 1"))));

        try {
            LobbyDTO lobby = lobbyService.joinLobby(1L, "Test UUID2","/Player 2/");
            Assertions.assertNotNull(lobby);
            Assertions.assertEquals(1, lobby.getLobbyID());
            Assertions.assertEquals("Player 1", lobby.getCurrentPlayer().getPlayerName());
            Assertions.assertEquals(2, lobby.getPlayers().size());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    void joinLobbyFull() {
        Lobby lobby = new Lobby(1L, new Player("Test UUID1", "Player 1"));
        lobby.addPlayer(new Player("Test UUID2", "Player 2"));
        lobby.addPlayer(new Player("Test UUID3", "Player 3"));
        lobby.addPlayer(new Player("Test UUID4", "Player 4"));
        Mockito.when(lobbyRepository.findById(1L)).thenReturn(Optional.of(lobby));

        Assertions.assertThrows(IllegalStateException.class, () -> lobbyService.joinLobby(lobby.getLobbyID(), "Test UUID5", "Player 5"));
    }

    @Test
    void joinLobbyNotFound() {
        Assertions.assertThrows(IllegalStateException.class, () -> lobbyService.joinLobby(1L, "Test UUID5", "Player 5"));
    }

    @Test
    void joinLobbyWhileInLobby() {
        Player player = new Player("Test UUID1", "Player 1");
        player.setLobbyID(1L);
        Lobby lobby = new Lobby(1L, player);
        Mockito.when(lobbyRepository.findById(1L)).thenReturn(Optional.of(lobby));
        Mockito.when(playerRepository.findById("Test UUID1")).thenReturn(Optional.of(player));
        Assertions.assertThrows(IllegalStateException.class, () -> lobbyService.joinLobby(lobby.getLobbyID(), "Test UUID1", "Player 1"));
    }

    @Test
    void leaveLobby() {
        Player player = new Player("Test UUID1", "Player 1");
        player.setLobbyID(1L);
        Lobby lobby = new Lobby(1L, player);
        Mockito.when(lobbyRepository.findById(1L)).thenReturn(Optional.of(lobby));
        Mockito.when(playerRepository.findById("Test UUID1")).thenReturn(Optional.of(player));

        try {
            LobbyDTO lobbyDTO = lobbyService.leaveLobby(player.getPlayerUUID());
            Assertions.assertEquals(1, lobbyDTO.getLobbyID());
        } catch (IllegalStateException e) {
            Assertions.fail(e);
        }
    }
}