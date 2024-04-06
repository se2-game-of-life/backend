package se2.group3.backend.domain.cells;

import se2.group3.backend.domain.player.Player;

import java.util.List;

// Cell for collecting salary
public class PaydayCell extends Cell {
    public PaydayCell(int position, List<Integer> nextCells) {
        super(position, nextCells);
    }


    @Override
    public void performAction(Player player) {
        // Player collects bonus
        player.setMoney(player.getMoney() + player.getcareerCard().getBonus());
        //TODO: implement collecting normal salary elsewhere
    }


}
