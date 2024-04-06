package se2.group3.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import se2.group3.backend.domain.cards.ActionCard;

public interface ActionCardRepository extends MongoRepository<ActionCard, String> {
    // Add custom query methods if needed
}
