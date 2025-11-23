package artishok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import artishok.entities.ExhibitionHallMap;

@Repository
public interface ExhibitionHallMapRepository extends JpaRepository<ExhibitionHallMap, Long> {
}