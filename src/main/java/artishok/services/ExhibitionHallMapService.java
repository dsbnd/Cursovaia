package artishok.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import artishok.entities.ExhibitionHallMap;
import artishok.repositories.ExhibitionHallMapRepository;

@Service
public class ExhibitionHallMapService {
    private final ExhibitionHallMapRepository exhibitionHallMapRepository;

    public ExhibitionHallMapService(ExhibitionHallMapRepository exhibitionHallMapRepository) {
        this.exhibitionHallMapRepository = exhibitionHallMapRepository;
    }

    public List<ExhibitionHallMap> getAllExhibitionHallMaps() {
        return exhibitionHallMapRepository.findAll();
    }

    public Optional<ExhibitionHallMap> getExhibitionHallMapById(Long id) {
        return exhibitionHallMapRepository.findById(id);
    }

    public List<ExhibitionHallMap> getExhibitionHallMapsByEventId(Long eventId) {
        return exhibitionHallMapRepository.findAll().stream()
                .filter(map -> map.getExhibitionEvent().getId().equals(eventId))
                .toList();
    }

    public ExhibitionHallMap saveExhibitionHallMap(ExhibitionHallMap exhibitionHallMap) {
        return exhibitionHallMapRepository.save(exhibitionHallMap);
    }

    public void deleteExhibitionHallMap(Long id) {
        exhibitionHallMapRepository.deleteById(id);
    }
}