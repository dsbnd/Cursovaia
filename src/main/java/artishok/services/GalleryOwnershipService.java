package artishok.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import artishok.entities.GalleryOwnership;
import artishok.repositories.GalleryOwnershipRepository;

@Service
public class GalleryOwnershipService {
    private final GalleryOwnershipRepository galleryOwnershipRepository;

    public GalleryOwnershipService(GalleryOwnershipRepository galleryOwnershipRepository) {
        this.galleryOwnershipRepository = galleryOwnershipRepository;
    }

    public List<GalleryOwnership> getAllGalleryOwnerships() {
        return galleryOwnershipRepository.findAll();
    }

    public Optional<GalleryOwnership> getGalleryOwnershipById(Long id) {
        return galleryOwnershipRepository.findById(id);
    }

    public List<GalleryOwnership> getGalleryOwnershipsByGalleryId(Long galleryId) {
        return galleryOwnershipRepository.findAll().stream()
                .filter(ownership -> ownership.getGallery().getId().equals(galleryId))
                .toList();
    }

    public List<GalleryOwnership> getGalleryOwnershipsByOwnerId(Long ownerId) {
        return galleryOwnershipRepository.findAll().stream()
                .filter(ownership -> ownership.getOwner().getId().equals(ownerId))
                .toList();
    }

    public GalleryOwnership saveGalleryOwnership(GalleryOwnership galleryOwnership) {
        return galleryOwnershipRepository.save(galleryOwnership);
    }

    public void deleteGalleryOwnership(Long id) {
        galleryOwnershipRepository.deleteById(id);
    }
}