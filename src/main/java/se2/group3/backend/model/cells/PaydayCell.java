package se2.group3.backend.model.cells;

import se2.group3.backend.model.Cell;
import se2.group3.backend.model.Player;

// Cell for collecting salary
public class PaydayCell extends Cell {
    public PaydayCell(int position) {
        super(position);
    }

    @Override
    public void performAction(Player player) {
        // Player collects bonus
        player.setMoney(player.getMoney() + player.getcareerCard().getBonus());
        //TODO: implement collecting normal salary elsewhere
    }


}
