package se2.group3.backend.model;

public class Bank {
    private int totalMoney; // Total money in the bank

    public Bank(int initialMoney) {
        totalMoney = initialMoney;
    }

    // Method to deduct funds from the bank
    public boolean deductFunds(int amount) {
        if (amount <= totalMoney) {
            totalMoney -= amount;
            return true; // Deduction successful
        }
        return false; // Insufficient funds
    }

    // Method to deposit funds into the bank
    public void depositFunds(int amount) {
        totalMoney += amount;
    }

    // Method to check the total money in the bank
    public int getTotalMoney() {
        return totalMoney;
    }
}
