package se.group3.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.group3.backend.domain.cards.HouseCard;

public interface HouseCardRepository extends MongoRepository<HouseCard, String> {
    // Add custom query methods if needed
}
