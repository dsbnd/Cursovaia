package artishok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import artishok.entities.GalleryOwnership;

@Repository
public interface GalleryOwnershipRepository extends JpaRepository<GalleryOwnership, Long> {
}