package se2.group3.backend.model;

public abstract class Cell {
    protected int position;
    protected Cell nextCell;

    public Cell(int position) {
        this.position = position;
    }

    public Cell(int position, Cell nextCell) {
        this.position = position;
        this.nextCell = nextCell;
    }

    public void setNextCell(Cell nextCell) {
        this.nextCell = nextCell;
    }

    public abstract void performAction(Player player);

    // Shared method for drawing a card and performing its action
    protected <T extends Card> void drawAndPerformCardAction(Player player, Deck<T> cardDeck) {
        T drawnCard = cardDeck.drawCard();
        System.out.println(player.getPlayerName() + " draws a card: " + drawnCard.getName());
        drawnCard.performAction(player);
    }

}

