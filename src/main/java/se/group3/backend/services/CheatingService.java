package se.group3.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.stereotype.Service;
import se.group3.backend.domain.Lobby;
import se.group3.backend.domain.Player;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.mapper.LobbyMapper;
import se.group3.backend.repositories.LobbyRepository;
import se.group3.backend.repositories.PlayerRepository;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CheatingService {

    private final LobbyRepository lobbyRepository;
    private final PlayerRepository playerRepository;

    private final int CHEATING_MONEY = 10000;
    private final int FALSE_REPORT_PENALTY = 5000;
    private final int CHEATING_COUGHT_PENALTY = 15000;
    private final int MAX_REPORT_TIME = 15;
    private final Set<String> cheatingQueue;
    private final ScheduledExecutorService scheduler;

    @Autowired
    public CheatingService(LobbyRepository lobbyRepository, PlayerRepository playerRepository) {
        this.lobbyRepository = lobbyRepository;
        this.playerRepository = playerRepository;
        this.cheatingQueue = ConcurrentHashMap.newKeySet();
        this.scheduler = Executors.newScheduledThreadPool(4);
    }

    public LobbyDTO cheat(String playerUUID) throws IllegalArgumentException {
        Player player = playerRepository.findById(playerUUID).orElse(null);
        if(player == null) throw new IllegalStateException("Player not found in repository: " + playerUUID);
        Lobby lobby = lobbyRepository.findById(player.getLobbyID()).orElse(null);
        if(lobby == null) throw new IllegalStateException("Lobby not found in repository: " + player.getLobbyID());

        for(Player playerInLobby : lobby.getPlayers()) {
            if(playerInLobby.getPlayerUUID().equals(playerUUID)) {
                playerInLobby.setMoney(player.getMoney() + CHEATING_MONEY);
                break;
            }
        }

        cheatingQueue.add(playerUUID);
        scheduler.schedule(() -> {
            cheatingQueue.remove(playerUUID);
        }, MAX_REPORT_TIME, TimeUnit.SECONDS);

        playerRepository.save(player);
        lobbyRepository.save(lobby);
        return LobbyMapper.toLobbyDTO(lobby);
    }

    public LobbyDTO report(String playerUUID, String reportUUID) throws IllegalArgumentException {
        Player player = playerRepository.findById(playerUUID).orElse(null);
        Player reportedPlayer = playerRepository.findById(reportUUID).orElse(null);
        if(reportedPlayer == null || player == null) throw new IllegalStateException("One of the involved players was not found in the repository!");
        Lobby lobby = lobbyRepository.findById(player.getLobbyID()).orElse(null);
        if(lobby == null) throw new IllegalStateException("Lobby not found in repository: " + player.getLobbyID());

        if(cheatingQueue.contains(reportedPlayer.getPlayerUUID())) {
            reportedPlayer.setMoney(reportedPlayer.getMoney() - CHEATING_COUGHT_PENALTY);
            cheatingQueue.remove(reportedPlayer.getPlayerUUID());
        } else {
            player.setMoney(player.getMoney() + FALSE_REPORT_PENALTY);
        }

        playerRepository.save(player);
        playerRepository.save(reportedPlayer);
        lobbyRepository.save(lobby);
        return LobbyMapper.toLobbyDTO(lobby);
    }
}
