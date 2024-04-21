package se.group3.backend.services.player;

import se.group3.backend.dto.PlayerDTO;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cells.Cell;
import se.group3.backend.domain.cells.StopCell;


/**
 * Interface for the PlayerService
 * Handles all the Actions a player is able to perform during the Game
 */
public interface PlayerService {

    /**
     * Determines if the Player chooses College
     * @param dto DTO to update the Player Object in the Database
     */
    void chooseCollagePath(PlayerDTO dto);

    /**
     * Determines if the Player chooses Marriage
     * @param dto DTO to update the Player Object in the Database
     */
    void chooseMarryPath(PlayerDTO dto);

    /**
     * Determines if the Player chooses to grow the Family
     * @param dto DTO to update the Player Object in the Database
     */
    void chooseGrowFamilyPath(PlayerDTO dto);

    /**
     * Determines if the Player gets a midlifecrisis
     * @param dto DTO to update the Player Object in the Database
     */
    void midLifeCrisisPath(PlayerDTO dto);

    /**
     * Determines if the Player chooses to retire early
     * @param dto DTO to update the Player Object in the Database
     */
    void chooseRetireEarlyPath(PlayerDTO dto);

    /**
     * Handles if the Player wants to increase the Number of Pegs
     * @param dto DTO to update the Player Object in the Database
     */
    void increaseNumberOfPegs(PlayerDTO dto);

    /**
     * Handles if the Player wants to Invest
     * @param dto DTO to update the Player Object in the Database
     * @param investmentNumber the number the player chooses. When the Number is spinnend during game, the Player receives a payout
     */
    void invest(PlayerDTO dto, Integer investmentNumber);

    /**
     * The Player collects a payout, if he invested earlier in the game
     * @param dto DTO to update the Player Object in the Database
     * @param spinResult the Result of the spin, it hast to macht with the players investmentNumber
     */
    void collectInvestmentPayout(PlayerDTO dto, Integer spinResult);

    /**
     * Gives a Player a Job, or allows him to change it
     * @param dto DTO to update the Player Object in the Database
     * @param careerCard the new Job the Player choose
     */
    void setOrUpdateCareer(PlayerDTO dto, CareerCard careerCard);

    /**
     * If the Player moves over a Payout cell, he receives a Payout. The Payout gets calculated by the Players chosen Job
     * @param dto DTO to update the Player Object in the Database
     * @param passedCell the Method has to check each cell the player passes.
     */
    void getPayOut(PlayerDTO dto, Cell passedCell);

    /**
     * Moves the player a random amount of fields between 1-10
     * @param dto DTO to update the Player Object in the Database
     */
    void spin(PlayerDTO dto);

    /**
     * checks the cell the player moves over, and performs the necessary action
     * @param dto DTO to update the Player Object in the Database
     * @param cell the cell, which has to be checked
     */
    void checkCellAndPerformAction(PlayerDTO dto, Cell cell);

    /**
     * checks the cell the player moves over, and performs the necessary action. Additionally, this method checks it the cell is a StopCell and stops the players move
     * @param dto DTO to update the Player Object in the Database
     * @param stopCell the stopCell, where the player has to be stopped
     */
    void checkStopCellAndPerformAction(PlayerDTO dto, StopCell stopCell);

}
