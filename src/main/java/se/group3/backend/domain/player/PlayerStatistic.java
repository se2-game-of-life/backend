package se.group3.backend.domain.player;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PlayerStatistic {
    private String playerName;
    private int money;
    private int investmentNumber;
    private int numberOfPegs;
    private int numberOfHouses;


    public PlayerStatistic(Player player) {
        this.playerName = player.getPlayerName();
        this.money = player.getMoney();
        this.investmentNumber = player.getInvestmentNumber();
        this.numberOfPegs = player.getNumberOfPegs();
        //todo: player needs a variable how many houses he has
        this.numberOfHouses = 0;
    }

}
