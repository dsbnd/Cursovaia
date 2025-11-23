package artishok.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import artishok.entities.ExhibitionHallMap;
import artishok.services.ExhibitionHallMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/exhibition-hall-maps")
public class ExhibitionHallMapController {
    private final ExhibitionHallMapService exhibitionHallMapService;

    public ExhibitionHallMapController(ExhibitionHallMapService exhibitionHallMapService) {
        this.exhibitionHallMapService = exhibitionHallMapService;
    }

    @GetMapping
    @Operation(summary = "Получить все карты выставочных залов")
    @ApiResponse(responseCode = "200", description = "Список карт успешно получен")
    @ApiResponse(responseCode = "204", description = "Карты не найдены")
    public ResponseEntity<List<ExhibitionHallMap>> getAllExhibitionHallMaps() {
        List<ExhibitionHallMap> maps = exhibitionHallMapService.getAllExhibitionHallMaps();
        if (maps.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(maps);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить карту зала по ID")
    @ApiResponse(responseCode = "200", description = "Карта найдена")
    @ApiResponse(responseCode = "404", description = "Карта не найдена")
    public ResponseEntity<ExhibitionHallMap> getExhibitionHallMapById(@PathVariable Long id) {
        Optional<ExhibitionHallMap> map = exhibitionHallMapService.getExhibitionHallMapById(id);
        return map.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Получить карты по ID события")
    @ApiResponse(responseCode = "200", description = "Карты найдены")
    @ApiResponse(responseCode = "204", description = "Карты не найдены")
    public ResponseEntity<List<ExhibitionHallMap>> getMapsByEventId(@PathVariable Long eventId) {
        List<ExhibitionHallMap> maps = exhibitionHallMapService.getExhibitionHallMapsByEventId(eventId);
        if (maps.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(maps);
    }

    @PostMapping
    @Operation(summary = "Создать новую карту выставочного зала")
    @ApiResponse(responseCode = "200", description = "Карта успешно создана")
    public ResponseEntity<ExhibitionHallMap> createExhibitionHallMap(@RequestBody ExhibitionHallMap exhibitionHallMap) {
        ExhibitionHallMap savedMap = exhibitionHallMapService.saveExhibitionHallMap(exhibitionHallMap);
        return ResponseEntity.ok(savedMap);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить карту выставочного зала")
    @ApiResponse(responseCode = "200", description = "Карта успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Карта не найдена")
    public ResponseEntity<ExhibitionHallMap> updateExhibitionHallMap(@PathVariable Long id, @RequestBody ExhibitionHallMap exhibitionHallMap) {
        if (!exhibitionHallMapService.getExhibitionHallMapById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        exhibitionHallMap.setId(id);
        ExhibitionHallMap updatedMap = exhibitionHallMapService.saveExhibitionHallMap(exhibitionHallMap);
        return ResponseEntity.ok(updatedMap);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить карту выставочного зала")
    @ApiResponse(responseCode = "200", description = "Карта успешно удалена")
    public ResponseEntity<Void> deleteExhibitionHallMap(@PathVariable Long id) {
        exhibitionHallMapService.deleteExhibitionHallMap(id);
        return ResponseEntity.ok().build();
    }
}