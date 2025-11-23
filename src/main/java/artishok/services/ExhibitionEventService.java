package artishok.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import artishok.entities.ExhibitionEvent;
import artishok.repositories.ExhibitionEventRepository;

@Service
public class ExhibitionEventService {
    private final ExhibitionEventRepository exhibitionEventRepository;

    public ExhibitionEventService(ExhibitionEventRepository exhibitionEventRepository) {
        this.exhibitionEventRepository = exhibitionEventRepository;
    }

    public List<ExhibitionEvent> getAllExhibitionEvents() {
        return exhibitionEventRepository.findAll();
    }

    public Optional<ExhibitionEvent> getExhibitionEventById(Long id) {
        return exhibitionEventRepository.findById(id);
    }

    public List<ExhibitionEvent> getExhibitionEventsByGalleryId(Long galleryId) {
        return exhibitionEventRepository.findAll().stream()
                .filter(event -> event.getGallery().getId().equals(galleryId))
                .toList();
    }

    public ExhibitionEvent saveExhibitionEvent(ExhibitionEvent exhibitionEvent) {
        return exhibitionEventRepository.save(exhibitionEvent);
    }

    public void deleteExhibitionEvent(Long id) {
        exhibitionEventRepository.deleteById(id);
    }
}