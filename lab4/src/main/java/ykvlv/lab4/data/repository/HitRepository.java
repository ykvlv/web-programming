package ykvlv.lab4.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ykvlv.lab4.data.entity.Hit;

public interface HitRepository extends JpaRepository<Hit, Long> {
}
