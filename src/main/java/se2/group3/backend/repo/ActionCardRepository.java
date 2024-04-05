package se2.group3.backend.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import se2.group3.backend.model.cards.ActionCard;

public interface ActionCardRepository extends MongoRepository<ActionCard, String> {
    // Add custom query methods if needed
}
