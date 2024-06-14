package se.group3.backend.repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import se.group3.backend.domain.cards.Card;
import se.group3.backend.domain.cards.HouseCard;

import java.util.ArrayList;
import java.util.List;

public interface HouseCardRepository extends MongoRepository<HouseCard, String> {
    // Add custom query methods if needed

    @Aggregation(pipeline = { "{$sample: {size: 1}}" })
    HouseCard findRandomHouseCard();

    default List<HouseCard> searchAffordableHousesForPlayer(int availableMoney){
        List<HouseCard> affordableHouses= new ArrayList<>();
        List<HouseCard> houses = findAll();

        for(HouseCard house : houses){
            if(house.getPurchasePrice() <= availableMoney && affordableHouses.size() < 2){
                affordableHouses.add(house);
            }
        }
        return affordableHouses;
    }

    /*
    private final HouseCardRepository houseCardRepository;

    To add to repo: houseCardRepository.save(houseCard);
    To delete from repo: houseCardRepository.deleteById("houseCardId");
    */
}
