package se.group3.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import se.group3.backend.domain.Player;
import se.group3.backend.domain.cards.Card;
import se.group3.backend.domain.cards.HouseCard;
import se.group3.backend.services.GameService;

import java.util.ArrayList;
import java.util.List;

public interface HouseCardRepository extends MongoRepository<HouseCard, String> {
    // Add custom query methods if needed

    @Query(value = "{'$sample': {'size': 1}}")
    HouseCard findRandomHouseCard();

    default List<Card> searchAffordableHousesForPlayer(Player player, GameService gameService){
        List<Card> houseCards = new ArrayList<>();
        HouseCard houseCard1 = findRandomHouseCard();
        HouseCard houseCard2 = findRandomHouseCard();
        while (player.getMoney() < houseCard1.getPurchasePrice() || player.getMoney() < houseCard2.getPurchasePrice()){
            if(player.getMoney() < houseCard1.getPurchasePrice()){
                houseCard1 = findRandomHouseCard();
            }
            if(player.getMoney() < houseCard2.getPurchasePrice()){
                houseCard2 = findRandomHouseCard();
            }
        }
        houseCards.add(houseCard1);
        houseCards.add(houseCard2);
        return houseCards;
    }

    /*
    private final HouseCardRepository houseCardRepository;

    To add to repo: houseCardRepository.save(houseCard);
    To delete from repo: houseCardRepository.deleteById("houseCardId");
    */
}
