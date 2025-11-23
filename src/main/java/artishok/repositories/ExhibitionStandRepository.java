package artishok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import artishok.entities.ExhibitionStand;

@Repository
public interface ExhibitionStandRepository extends JpaRepository<ExhibitionStand, Long> {
}