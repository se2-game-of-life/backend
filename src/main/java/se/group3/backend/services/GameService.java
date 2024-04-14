package se.group3.backend.services;

import se.group3.backend.DTOs.PlayerDTO;
import se.group3.backend.dto.CellDTO;
import se.group3.backend.dto.LobbyDTO;

/**
 * Interface for the GameService
 * Handles all the actions that are needed in a game
 */
public interface GameService {

    /**
     * Starts a new game
     */
    void startGame();

    /**
     * Determines if the Player chooses College
     * @param playerDTO to update the player Object in the database
     */
    void choosePath(PlayerDTO playerDTO);

    /**
     * Determines what happens on the cell
     * @param playerDTO to update the Player Object in the Database
     * @param cellDTO to know which cell has just been stepped on
     */
    void handleCell(PlayerDTO playerDTO, CellDTO cellDTO);

    /**
     * Show statistics of other players
     * @param lobbyDTO to use the PlayerDTO[]
     */

    void getPlayerStats(LobbyDTO lobbyDTO);



}
