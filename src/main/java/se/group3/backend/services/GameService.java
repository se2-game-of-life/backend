package se.group3.backend.services;


import se.group3.backend.domain.cells.Cell;
import se.group3.backend.domain.player.PlayerStatistic;
import se.group3.backend.dto.CellDTO;
import se.group3.backend.dto.LobbyDTO;
import se.group3.backend.dto.PlayerDTO;

import java.util.List;

/**
 * Interface for the GameService
 * Handles all the actions that are needed in a game
 */
public interface GameService {

    void choosePath(String playerUUID, long lobbyID);

    void chooseAction(String playerUUID, long lobbyID, boolean pickOptionOne);

    /**
     * Show statistics of other players
     *
     * @param lobbyDTO  to use the PlayerDTO[]
     * @param playerDTO to know for which player the statistic is shown
     * @return otherPlayersStats: returns a list with PlayerStatistic objects
     */

    List<PlayerStatistic> getPlayerStats(PlayerDTO playerDTO, LobbyDTO lobbyDTO);

    void spinWheel(String playerUUID, long lobbyID);

    void handleMove(PlayerDTO playerDTO, List<Cell> cells);
}