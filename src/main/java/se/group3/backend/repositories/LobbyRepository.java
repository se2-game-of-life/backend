package se.group3.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.group3.backend.domain.lobby.Lobby;

public interface LobbyRepository extends MongoRepository<Lobby, Long> {}
