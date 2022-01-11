package ykvlv.lab4.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ykvlv.lab4.data.entity.Operation;

public interface OperationRepository extends JpaRepository<Operation, String> {
}
