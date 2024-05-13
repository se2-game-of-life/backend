package se.group3.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.group3.backend.domain.Lobby;

public interface LobbyRepository extends MongoRepository<Lobby, Long> {}
