package artishok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import artishok.entities.ExhibitionEvent;

@Repository
public interface ExhibitionEventRepository extends JpaRepository<ExhibitionEvent, Long> {
}