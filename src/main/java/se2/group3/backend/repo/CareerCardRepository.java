package se2.group3.backend.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import se2.group3.backend.model.cards.CareerCard;

public interface CareerCardRepository extends MongoRepository<CareerCard, String> {
    // Add custom query methods if needed
}
