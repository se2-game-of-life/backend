package se.group3.backend.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.group3.backend.domain.Lobby;
import se.group3.backend.domain.Player;
import se.group3.backend.repositories.LobbyRepository;
import se.group3.backend.repositories.PlayerRepository;
import se.group3.backend.services.CheatingService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheatingServiceTest {
    private PlayerRepository playerRepository;
    private LobbyRepository lobbyRepository;
    private CheatingService cheatingService;
    private final Player player = new Player("UUID", "Player1");
    private final Player reportedPlayer = new Player("reportedUUID", "Player2");
    private Lobby lobby;

    @BeforeEach
    void setUp(){
        this.lobbyRepository = mock(LobbyRepository.class);
        this.playerRepository = mock(PlayerRepository.class);
        this.cheatingService = new CheatingService(lobbyRepository, playerRepository);
        lobby = new Lobby(1L, player);
        player.setLobbyID(1L);
        reportedPlayer.setLobbyID(1L);
    }

    @Test
    void cheat_playerNotFound_test() {
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.empty());
        String playerUUID = player.getPlayerUUID();

        Exception e = assertThrows(IllegalStateException.class, () -> cheatingService.cheat(playerUUID));

        assertEquals("Player not found in repository: " + player.getPlayerUUID(), e.getMessage());
    }

    @Test
    void cheat_lobbyNotFound_test() {
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(lobby.getLobbyID())).thenReturn(Optional.empty());
        String playerUUID = player.getPlayerUUID();

        Exception e = assertThrows(IllegalStateException.class, () -> cheatingService.cheat(playerUUID));

        assertEquals("Lobby not found in repository: " + lobby.getLobbyID(), e.getMessage());
    }

    @Test
    void cheat_successful_test() {
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));
        when(lobbyRepository.findById(lobby.getLobbyID())).thenReturn(Optional.of(lobby));
        player.setMoney(0);
        lobby.setPlayers(List.of(player));
        String playerUUID = player.getPlayerUUID();

        assertDoesNotThrow(() -> {cheatingService.cheat(playerUUID);});

        assertEquals(10000, player.getMoney());
        verify(lobbyRepository).save(lobby);
    }

    @Test
    void report_playerNotFound_test() {
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.empty());
        String playerUUID = player.getPlayerUUID();

        Exception e = assertThrows(IllegalStateException.class, () -> cheatingService.report(playerUUID, playerUUID));

        assertEquals("One of the involved players was not found in the repository!", e.getMessage());
    }

    @Test
    void report_reportedPlayerNotFound_test() {
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));
        when(playerRepository.findById(reportedPlayer.getPlayerUUID())).thenReturn(Optional.empty());
        String playerUUID = player.getPlayerUUID();
        String reportedPlayerUUID = reportedPlayer.getPlayerUUID();

        Exception e = assertThrows(IllegalStateException.class, () -> cheatingService.report(playerUUID, reportedPlayerUUID));

        assertEquals("One of the involved players was not found in the repository!", e.getMessage());
    }

    @Test
    void report_lobbyNotFound_test() {
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));
        when(playerRepository.findById(reportedPlayer.getPlayerUUID())).thenReturn(Optional.of(reportedPlayer));
        when(lobbyRepository.findById(lobby.getLobbyID())).thenReturn(Optional.empty());
        String playerUUID = player.getPlayerUUID();
        String reportedPlayerUUID = reportedPlayer.getPlayerUUID();

        Exception e = assertThrows(IllegalStateException.class, () -> cheatingService.report(playerUUID, reportedPlayerUUID));

        assertEquals("Lobby not found in repository: " + lobby.getLobbyID(), e.getMessage());
    }

    @Test
    void report_successful_test() {
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));
        when(playerRepository.findById(reportedPlayer.getPlayerUUID())).thenReturn(Optional.of(reportedPlayer));
        when(lobbyRepository.findById(lobby.getLobbyID())).thenReturn(Optional.of(lobby));
        lobby.setPlayers(List.of(player, reportedPlayer));
        reportedPlayer.setMoney(15000);
        cheatingService.getCheatingQueue().add(reportedPlayer.getPlayerUUID());
        String playerUUID = player.getPlayerUUID();
        String reportedPlayerUUID = reportedPlayer.getPlayerUUID();

        assertDoesNotThrow(() -> {cheatingService.report(playerUUID, reportedPlayerUUID);});

        assertEquals(0, reportedPlayer.getMoney());
        verify(lobbyRepository).save(lobby);
    }

    @Test
    void report_successful_falseReport_test() {
        when(playerRepository.findById(player.getPlayerUUID())).thenReturn(Optional.of(player));
        when(playerRepository.findById(reportedPlayer.getPlayerUUID())).thenReturn(Optional.of(reportedPlayer));
        when(lobbyRepository.findById(lobby.getLobbyID())).thenReturn(Optional.of(lobby));
        lobby.setPlayers(List.of(player, reportedPlayer));
        reportedPlayer.setMoney(15000);
        player.setMoney(10000);
        cheatingService.setCheatingQueue(new HashSet<>());

        String playerUUID = player.getPlayerUUID();
        String reportedPlayerUUID = reportedPlayer.getPlayerUUID();

        assertDoesNotThrow(() -> {cheatingService.report(playerUUID, reportedPlayerUUID);});

        assertEquals(15000, reportedPlayer.getMoney());
        assertEquals(5000, player.getMoney());
        verify(lobbyRepository).save(lobby);
    }


    @AfterEach
    void breakDown(){
        this.lobbyRepository = null;
        this.playerRepository = null;
        this.cheatingService = null;
    }



}
