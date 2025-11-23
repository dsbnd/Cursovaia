package artishok.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import artishok.entities.ExhibitionStand;
import artishok.services.ExhibitionStandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/exhibition-stands")
public class ExhibitionStandController {
    private final ExhibitionStandService exhibitionStandService;

    public ExhibitionStandController(ExhibitionStandService exhibitionStandService) {
        this.exhibitionStandService = exhibitionStandService;
    }

    @GetMapping
    @Operation(summary = "Получить все выставочные стенды")
    @ApiResponse(responseCode = "200", description = "Список стендов успешно получен")
    @ApiResponse(responseCode = "204", description = "Стенды не найдены")
    public ResponseEntity<List<ExhibitionStand>> getAllExhibitionStands() {
        List<ExhibitionStand> stands = exhibitionStandService.getAllExhibitionStands();
        if (stands.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(stands);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить стенд по ID")
    @ApiResponse(responseCode = "200", description = "Стенд найден")
    @ApiResponse(responseCode = "404", description = "Стенд не найден")
    public ResponseEntity<ExhibitionStand> getExhibitionStandById(@PathVariable Long id) {
        Optional<ExhibitionStand> stand = exhibitionStandService.getExhibitionStandById(id);
        return stand.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hall-map/{hallMapId}")
    @Operation(summary = "Получить стенды по ID карты зала")
    @ApiResponse(responseCode = "200", description = "Стенды найдены")
    @ApiResponse(responseCode = "204", description = "Стенды не найдены")
    public ResponseEntity<List<ExhibitionStand>> getStandsByHallMapId(@PathVariable Long hallMapId) {
        List<ExhibitionStand> stands = exhibitionStandService.getExhibitionStandsByHallMapId(hallMapId);
        if (stands.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(stands);
    }

    @PostMapping
    @Operation(summary = "Создать новый выставочный стенд")
    @ApiResponse(responseCode = "200", description = "Стенд успешно создан")
    public ResponseEntity<ExhibitionStand> createExhibitionStand(@RequestBody ExhibitionStand exhibitionStand) {
        ExhibitionStand savedStand = exhibitionStandService.saveExhibitionStand(exhibitionStand);
        return ResponseEntity.ok(savedStand);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить выставочный стенд")
    @ApiResponse(responseCode = "200", description = "Стенд успешно обновлен")
    @ApiResponse(responseCode = "404", description = "Стенд не найден")
    public ResponseEntity<ExhibitionStand> updateExhibitionStand(@PathVariable Long id, @RequestBody ExhibitionStand exhibitionStand) {
        if (!exhibitionStandService.getExhibitionStandById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        exhibitionStand.setId(id);
        ExhibitionStand updatedStand = exhibitionStandService.saveExhibitionStand(exhibitionStand);
        return ResponseEntity.ok(updatedStand);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить выставочный стенд")
    @ApiResponse(responseCode = "200", description = "Стенд успешно удален")
    public ResponseEntity<Void> deleteExhibitionStand(@PathVariable Long id) {
        exhibitionStandService.deleteExhibitionStand(id);
        return ResponseEntity.ok().build();
    }
}
