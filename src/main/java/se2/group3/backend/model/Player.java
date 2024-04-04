package se2.group3.backend.model;


public class Player {

    private String playerName;
    private int currentCellPosition;
    private int money;
    private boolean isCollegePath; // Indicates if the player chose the college path
    private int investmentNumber; // The chosen investment number
    private int investmentLevel; // The current level of investment

    // Constructor
    public Player(String playerName) {
        this.playerName = playerName;
        this.currentCellPosition = 0; 
        this.money = 250000; // Starting money
        this.isCollegePath = false; 
        this.investmentNumber =0;
        this.investmentLevel = 0; 
    }

    //TODO: Getter and setter methods
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
    
}