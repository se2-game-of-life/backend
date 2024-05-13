package se.group3.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import se.group3.backend.domain.cards.ActionCard;

public interface ActionCardRepository extends MongoRepository<ActionCard, String> {
    // Add custom query methods if needed

    @Query(value = "{'$sample': {'size': 1}}")
    ActionCard findRandomActionCard();
}
