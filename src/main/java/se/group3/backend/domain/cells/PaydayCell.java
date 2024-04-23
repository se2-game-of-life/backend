package se.group3.backend.domain.cells;

import se.group3.backend.domain.player.Player;

import java.util.List;

// Cell for collecting salary
public class PaydayCell extends Cell {

    public PaydayCell(int position, String type, List<Integer> nextCells, int row, int col) {
        super(position, type, nextCells, row, col);
    }



    @Override
    public void performAction(Player player) {
        // Player collects bonus
        player.setMoney(player.getMoney() + player.getCareerCard().getBonus());
        //TODO: implement collecting normal salary elsewhere
    }


}
