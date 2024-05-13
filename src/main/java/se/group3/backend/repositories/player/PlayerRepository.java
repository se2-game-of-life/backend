package se.group3.backend.repositories.player;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.group3.backend.domain.Player;

/**
 * Repository to handle updates on the Player Object
 */
public interface PlayerRepository extends MongoRepository<Player, String> {
}
