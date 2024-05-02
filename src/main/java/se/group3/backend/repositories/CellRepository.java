package se.group3.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.group3.backend.domain.game.Cell;

public interface CellRepository extends MongoRepository<Cell, String> {
    Cell findByNumber(int number);
}
