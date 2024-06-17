package se.group3.backend.services;

import se.group3.backend.domain.Cell;
import se.group3.backend.domain.CellType;
import se.group3.backend.domain.Lobby;
import se.group3.backend.domain.Player;
import se.group3.backend.domain.cards.CareerCard;
import se.group3.backend.domain.cards.HouseCard;
import se.group3.backend.repositories.CareerCardRepository;
import se.group3.backend.repositories.CellRepository;
import se.group3.backend.repositories.HouseCardRepository;

import java.util.ArrayList;
import java.util.List;

public class PlayerService {
    private final CareerCardRepository careerCardRepository;
    private final  CellRepository cellRepository;
    private final HouseCardRepository houseCardRepository;


    public PlayerService(CareerCardRepository careerCardRepository, CellRepository cellRepository, HouseCardRepository houseCardRepository) {
        this.careerCardRepository = careerCardRepository;
        this.cellRepository = cellRepository;
        this.houseCardRepository = houseCardRepository;
    }

    public void midLife(Player player, Cell cell, int randomNumber){
        if(randomNumber > 2) {
            player.setCurrentCellPosition(cell.getNextCells().get(0));
        } else {
            player.setCurrentCellPosition(cell.getNextCells().get(1));
        }
    }

    public void getCareerCards(Player player, Lobby lobby){
        List<CareerCard> careerCards = new ArrayList<>();
        if(player.isCollegeDegree()){
            careerCards.add(careerCardRepository.findRandomCareerCard());
            careerCards.add(careerCardRepository.findRandomCareerCard());
        } else{
            while(careerCards.size() < 2){
                CareerCard card = careerCardRepository.findRandomCareerCard();
                if(!card.needsDiploma()){
                    careerCards.add(card);
                }
            }
        }
        lobby.setCareerCards(careerCards);
        lobby.setHasDecision(true);
    }


    public void getHouseCards(Player player, Lobby lobby){
        List<HouseCard> houseCards = houseCardRepository.searchAffordableHousesForPlayer(player.getMoney());
        if(houseCards.size() != 2) {
            lobby.setHasDecision(false);
        } else{
            lobby.setHouseCards(houseCards);
            lobby.setHasDecision(true);
        }
    }

    public void retire(Player player, Lobby lobby, int spunNumber){
        List<Player> players = lobby.getPlayers();
        int counter = 0;
        for(Player p : players){
            Cell currentCell = cellRepository.findByNumber(p.getCurrentCellPosition());
            if(currentCell.getType() == CellType.RETIREMENT){
                counter++;
            }
        }

        switch (counter){
            case(1):
                player.setMoney(player.getMoney()+200000);
                break;
            case(2):
                player.setMoney(player.getMoney()+100000);
                break;
            case(3):
                player.setMoney(player.getMoney()+50000);
                break;
            case(4):
                player.setMoney(player.getMoney()+10000);
                break;
            default:
        }

        List<HouseCard> playerHouses = player.getHouses();
        for(HouseCard h : playerHouses){
            if(spunNumber%2 == 0){
                player.setMoney(player.getMoney()+h.getBlackSellPrice());
            } else{
                player.setMoney(player.getMoney()+h.getRedSellPrice());
            }
        }
        player.setHouses(new ArrayList<>());

        player.setMoney(player.getMoney()+(player.getNumberOfPegs()*50000));

    }

    public void retireEarly(Player player, Cell cell, boolean chooseLeft){
        if(chooseLeft) {
            player.setCurrentCellPosition(cell.getNextCells().get(0));
        } else {
            player.setCurrentCellPosition(cell.getNextCells().get(1));
        }
    }



    public void careerOrCollegeChoice(Player player, boolean chooseLeft){
        if(chooseLeft) {
            player.setMoney(player.getMoney() - 100000);
            player.setCurrentCellPosition(1);
        } else {
            player.setCurrentCellPosition(14);
            player.setCareerCard(careerCardRepository.findCareerCardNoDiploma());
        }
        player.setCollegeDegree(chooseLeft);
    }

    public void marryAndFamilyPathChoice(Player player, boolean chooseLeft, Cell cell){
        if(chooseLeft){
            player.setMoney(player.getMoney()- 50000);
            player.setNumberOfPegs(player.getNumberOfPegs() + 1);
            player.setCurrentCellPosition(cell.getNextCells().get(0));
        } else{
            player.setCurrentCellPosition(cell.getNextCells().get(1));
        }
    }

    public void houseChoice(Player player, Lobby lobby, boolean chooseLeft){
        List<HouseCard> houseCardList = lobby.getHouseCards();
        HouseCard houseCard;
        if(chooseLeft){
            houseCard = houseCardList.get(0);
        } else{
            houseCard = houseCardList.get(1);
        }
        player.setMoney(player.getMoney()-houseCard.getPurchasePrice());
        if(player.getHouses() != null){
            List<HouseCard> playerHouseCards = player.getHouses();
            playerHouseCards.add(houseCard);
            player.setHouses(playerHouseCards);
        } else{
            player.setHouses(List.of(houseCard));
        }
    }

    public void careerChoice(Player player, Lobby lobby, boolean chooseLeft){
        List<CareerCard> careerCardList = lobby.getCareerCards();
        CareerCard careerCard;
        if(chooseLeft){
            careerCard = careerCardList.get(0);
        } else{
            careerCard = careerCardList.get(1);
        }
        player.setCareerCard(careerCard);
    }

}
