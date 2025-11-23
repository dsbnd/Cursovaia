package artishok.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import artishok.entities.Gallery;
import artishok.repositories.GalleryRepository;

@Service
public class GalleryService {
    private final GalleryRepository galleryRepository;

    public GalleryService(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    public List<Gallery> getAllGalleries() {
        return galleryRepository.findAll();
    }

    public Optional<Gallery> getGalleryById(Long id) {
        return galleryRepository.findById(id);
    }

    public Gallery saveGallery(Gallery gallery) {
        return galleryRepository.save(gallery);
    }

    public void deleteGallery(Long id) {
        galleryRepository.deleteById(id);
    }
}