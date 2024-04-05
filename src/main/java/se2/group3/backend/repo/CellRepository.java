package se2.group3.backend.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import se2.group3.backend.model.Cell;

public interface CellRepository extends MongoRepository<Cell, String> {
}
