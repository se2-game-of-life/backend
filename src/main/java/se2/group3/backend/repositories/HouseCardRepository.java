package se2.group3.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import se2.group3.backend.domain.cards.HouseCard;

public interface HouseCardRepository extends MongoRepository<HouseCard, String> {
    // Add custom query methods if needed
}
