package se.group3.backend.services.player;

import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;


/**
 * Interface for the PlayerService
 * Handles all the Actions a player is able to perform during the Game
 */
public interface PlayerService {

    /**
     * Handles if the Player wants to increase the Number of Pegs
     * @param playerID the ID of the Player
     */
    void increaseNumberOfPegs(String playerID);

    /**
     * Gives a Player a Job, or allows him to change it
     * @param playerID the ID of the Player
     * @param careerCard the new Job the Player choose
     */
    void setCareer(String playerID, CareerCard careerCard);

    /**
     * If the Player moves over a Payout cell, he receives a Payout. The Payout gets calculated by the Players chosen Job
     * @param playerID the ID of the Player
     */
    void getSalary(String playerID);

    /**
     * if the Player steps on a Payday Cell, he receives a Bonus.
     * @param playerID the ID of the Player
     */
    void getSalaryWithBonus(String playerID);

    /**
     * If the Player wants to buy a House, this method handles the transaction
     * @param playerID the ID of the Player
     * @param houseCard the House the Player wants to buy
     */
    void addHouse(String playerID, HouseCard houseCard);
}
