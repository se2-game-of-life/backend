package se.group3.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.group3.backend.domain.cards.HouseCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface HouseCardRepository extends MongoRepository<HouseCard, String> {
    Random random = new Random();

    default List<HouseCard> searchAffordableHousesForPlayer(int availableMoney) {
        List<HouseCard> affordableHouses = new ArrayList<>();
        List<HouseCard> houses = findAll();


        int attempts = 0;
        int maxAttempts = houses.size() * 2;

        while (affordableHouses.size() < 2 && attempts < maxAttempts) {
            int randomIndex = random.nextInt(houses.size());
            HouseCard house = houses.get(randomIndex);
            if (house.getPurchasePrice() <= availableMoney) {
                affordableHouses.add(house);
            }
            attempts++;
        }

        return affordableHouses;
    }

}
