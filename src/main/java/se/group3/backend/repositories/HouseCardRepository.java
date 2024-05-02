package se.group3.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import se.group3.backend.domain.cards.HouseCard;

public interface HouseCardRepository extends MongoRepository<HouseCard, String> {
    // Add custom query methods if needed

    @Query(value = "{'$sample': {'size': 1}}")
    HouseCard findRandomHouseCard();

    /*
    private final HouseCardRepository houseCardRepository;

    To add to repo: houseCardRepository.save(houseCard);
    To delete from repo: houseCardRepository.deleteById("houseCardId");
    */
}
