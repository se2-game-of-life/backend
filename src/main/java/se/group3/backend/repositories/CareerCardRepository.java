package se.group3.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.group3.backend.domain.cards.CareerCard;

public interface CareerCardRepository extends MongoRepository<CareerCard, String> {
    // Add custom query methods if needed
}
