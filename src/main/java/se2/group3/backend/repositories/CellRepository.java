package se2.group3.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import se2.group3.backend.domain.cells.Cell;

public interface CellRepository extends MongoRepository<Cell, String> {
}
