package se.group3.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.group3.backend.domain.cells.Cell;

public interface CellRepository extends MongoRepository<Cell, String> {
}
