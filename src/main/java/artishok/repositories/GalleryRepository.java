package artishok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import artishok.entities.Gallery;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}