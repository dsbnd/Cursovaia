package artishok.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import artishok.entities.ExhibitionStand;
import artishok.repositories.ExhibitionStandRepository;

@Service
public class ExhibitionStandService {
    private final ExhibitionStandRepository exhibitionStandRepository;

    public ExhibitionStandService(ExhibitionStandRepository exhibitionStandRepository) {
        this.exhibitionStandRepository = exhibitionStandRepository;
    }

    public List<ExhibitionStand> getAllExhibitionStands() {
        return exhibitionStandRepository.findAll();
    }

    public Optional<ExhibitionStand> getExhibitionStandById(Long id) {
        return exhibitionStandRepository.findById(id);
    }

    public List<ExhibitionStand> getExhibitionStandsByHallMapId(Long hallMapId) {
        return exhibitionStandRepository.findAll().stream()
                .filter(stand -> stand.getExhibitionHallMap().getId().equals(hallMapId))
                .toList();
    }

    public ExhibitionStand saveExhibitionStand(ExhibitionStand exhibitionStand) {
        return exhibitionStandRepository.save(exhibitionStand);
    }

    public void deleteExhibitionStand(Long id) {
        exhibitionStandRepository.deleteById(id);
    }
}
