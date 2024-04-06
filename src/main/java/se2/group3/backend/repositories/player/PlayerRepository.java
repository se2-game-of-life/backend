package se2.group3.backend.repositories.player;

import org.springframework.data.mongodb.repository.MongoRepository;
import se2.group3.backend.domain.player.Player;

public interface PlayerRepository extends MongoRepository<Player, String> {
}
