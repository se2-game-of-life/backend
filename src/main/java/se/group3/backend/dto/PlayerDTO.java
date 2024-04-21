package se.group3.backend.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import se.group3.backend.domain.cards.CareerCard;


@NoArgsConstructor
public class PlayerDTO {

    @JsonCreator
    public PlayerDTO(@JsonProperty("playerName") String playerName) {
        this.playerName = playerName;
    }


    private String playerID;
    private String playerName;
    private int currentCellPosition;
    private int money;

    private CareerCard careerCard;

    // Booleans for the different paths the game offers
    private boolean isCollegePath;
    private boolean isMarriedPath;
    private boolean isGrowFamilyPath;
    private boolean hasMidlifeCrisis;
    private boolean isRetireEarlyPath;

    private int investmentNumber; // The chosen investment number
    private int investmentLevel; // The current level of investment-
    private int numberOfPegs;

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getCurrentCellPosition() {
        return currentCellPosition;
    }

    public void setCurrentCellPosition(int currentCellPosition) {
        this.currentCellPosition = currentCellPosition;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public CareerCard getCareerCard() {
        return careerCard;
    }

    public void setCareerCard(CareerCard careerCard) {
        this.careerCard = careerCard;
    }

    public int getInvestmentNumber() {
        return investmentNumber;
    }

    public void setInvestmentNumber(int investmentNumber) {
        this.investmentNumber = investmentNumber;
    }

    public int getInvestmentLevel() {
        return investmentLevel;
    }

    public void setInvestmentLevel(int investmentLevel) {
        this.investmentLevel = investmentLevel;
    }

    public int getNumberOfPegs() {
        return numberOfPegs;
    }

    public void setNumberOfPegs(int numberOfPegs) {
        this.numberOfPegs = numberOfPegs;
    }

    public boolean isCollegePath() {
        return isCollegePath;
    }

    public void setCollegePath(boolean collegePath) {
        isCollegePath = collegePath;
    }

    public boolean isMarriedPath() {
        return isMarriedPath;
    }

    public void setMarriedPath(boolean marriedPath) {
        isMarriedPath = marriedPath;
    }

    public boolean isGrowFamilyPath() {
        return isGrowFamilyPath;
    }

    public void setGrowFamilyPath(boolean growFamilyPath) {
        isGrowFamilyPath = growFamilyPath;
    }

    public boolean isHasMidlifeCrisis() {
        return hasMidlifeCrisis;
    }

    public void setHasMidlifeCrisis(boolean hasMidlifeCrisis) {
        this.hasMidlifeCrisis = hasMidlifeCrisis;
    }

    public boolean isRetireEarlyPath() {
        return isRetireEarlyPath;
    }

    public void setRetireEarlyPath(boolean retireEarlyPath) {
        isRetireEarlyPath = retireEarlyPath;
    }
}