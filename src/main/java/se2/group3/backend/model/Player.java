package se2.group3.backend.model;

import se2.group3.backend.model.cards.CareerCard;

import java.util.List;

public class Player {

    private String playerName;
    private int currentCellPosition;
    private int money;

    private CareerCard careerCard;
    private boolean isCollegePath; // Indicates if the player chose the college path
    private int investmentNumber; // The chosen investment number
    private int investmentLevel; // The current level of investment
    private int numberOfPegs;

    // Constructor
    public Player(String playerName) {
        this.playerName = playerName;
        this.currentCellPosition = 0; 
        this.money = 250000; // Starting money
        this.isCollegePath = false; 
        this.investmentNumber =0;
        this.investmentLevel = 0;
        this.numberOfPegs = 1;
    }

    // Method to choose collage path
    public void chooseCollagePath() {
        if(! isCollegePath){
            this.isCollegePath = true;
            this.money -= 100000;
        }
    }

    public void increaseNumberOfPegs() {
        this.numberOfPegs += 1;
    }

    // Method to handle investment
    public void invest(int investmentNumber) {
        this.money -= 50000;          // Pay 50K to the bank      
        this.investmentNumber = investmentNumber; // Set the investment number
    }

    // Method to collect investment payout
    public void collectInvestmentPayout(int spinResult) {
        if (spinResult == investmentNumber) {
            this.money += (investmentLevel + 1) * 10; // Payment based on investment level
            investmentLevel++; // Increase investment level
        }
    }

    // TODO: Method to receive career cards and choose one
    public void receiveCareerCards(List<CareerCard> careerCards) {
        // Assuming CareerCard class is defined separately and extends Card
        // Here, you'd receive the top two Career cards from the deck
        // Let's assume you have access to the deck of Career cards
        // Then you can deal the top two cards to the player
    }



    // Getter and setter methods
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
    public int getNumberOfPegs() {
        return numberOfPegs;
    }
    public CareerCard getcareerCard() {
        return careerCard;
    }
    public boolean isCollegePath() {
        return isCollegePath;
    }
}