package se.group3.backend.services;


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

    /**
     * Starts a new game, initializes conditions and determines the order in which players spin
     * @param lobbyDTO to use Lobby Object
     */
    void startGame(LobbyDTO lobbyDTO);

    /**
     * Determines if the Player chooses College or Career
     * @param playerUUID to update the player in the database
     * @param collegePath to show if player chooses collegePath
     */
    String choosePath(String playerUUID, boolean collegePath);

    /**
     * Determines what happens on the cell
     * @param playerDTO to update the Player Object in the Database
     * @param cellDTO to know which cell has just been stepped on
     */
    void handleCell(PlayerDTO playerDTO, CellDTO cellDTO);

    /**
     * Show statistics of other players
     * @param lobbyDTO to use the PlayerDTO[]
     * @param playerDTO to know for which player the statistic is shown
     * @return otherPlayersStats: returns a list with PlayerStatistic objects
     */

    List<PlayerStatistic> getPlayerStats(PlayerDTO playerDTO, LobbyDTO lobbyDTO);

    /**
     * Shows who won the game and which place the player finished in
     * @param playerDTO to user Player Object from Database
     */
    void checkWinCondition(PlayerDTO playerDTO);

    /**
     * Implements the functionality of spinning the wheel
     * @param playerDTO to update the Player Object in the Database
     */
    void spinWheel(PlayerDTO playerDTO);

    /**
     * Ends the player's turn and switches to the next player
     * @param playerDTO Player who has just finished the turn
     */
    void nextPlayer(PlayerDTO playerDTO);

}
