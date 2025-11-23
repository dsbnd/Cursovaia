package artishok.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import artishok.entities.GalleryOwnership;
import artishok.services.GalleryOwnershipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/gallery-ownerships")
public class GalleryOwnershipController {
    private final GalleryOwnershipService galleryOwnershipService;

    public GalleryOwnershipController(GalleryOwnershipService galleryOwnershipService) {
        this.galleryOwnershipService = galleryOwnershipService;
    }

    @GetMapping
    @Operation(summary = "Получить все записи о владении галереями")
    @ApiResponse(responseCode = "200", description = "Список записей успешно получен")
    @ApiResponse(responseCode = "204", description = "Записи не найдены")
    public ResponseEntity<List<GalleryOwnership>> getAllGalleryOwnerships() {
        List<GalleryOwnership> ownerships = galleryOwnershipService.getAllGalleryOwnerships();
        if (ownerships.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ownerships);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить запись о владении по ID")
    @ApiResponse(responseCode = "200", description = "Запись найдена")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<GalleryOwnership> getGalleryOwnershipById(@PathVariable Long id) {
        Optional<GalleryOwnership> ownership = galleryOwnershipService.getGalleryOwnershipById(id);
        return ownership.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/gallery/{galleryId}")
    @Operation(summary = "Получить владения по ID галереи")
    @ApiResponse(responseCode = "200", description = "Владения найдены")
    @ApiResponse(responseCode = "204", description = "Владения не найдены")
    public ResponseEntity<List<GalleryOwnership>> getOwnershipsByGalleryId(@PathVariable Long galleryId) {
        List<GalleryOwnership> ownerships = galleryOwnershipService.getGalleryOwnershipsByGalleryId(galleryId);
        if (ownerships.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ownerships);
    }

    @GetMapping("/owner/{ownerId}")
    @Operation(summary = "Получить владения по ID владельца")
    @ApiResponse(responseCode = "200", description = "Владения найдены")
    @ApiResponse(responseCode = "204", description = "Владения не найдены")
    public ResponseEntity<List<GalleryOwnership>> getOwnershipsByOwnerId(@PathVariable Long ownerId) {
        List<GalleryOwnership> ownerships = galleryOwnershipService.getGalleryOwnershipsByOwnerId(ownerId);
        if (ownerships.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ownerships);
    }

    @PostMapping
    @Operation(summary = "Создать новую запись о владении галереей")
    @ApiResponse(responseCode = "200", description = "Запись успешно создана")
    public ResponseEntity<GalleryOwnership> createGalleryOwnership(@RequestBody GalleryOwnership galleryOwnership) {
        GalleryOwnership savedOwnership = galleryOwnershipService.saveGalleryOwnership(galleryOwnership);
        return ResponseEntity.ok(savedOwnership);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить запись о владении галереей")
    @ApiResponse(responseCode = "200", description = "Запись успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<GalleryOwnership> updateGalleryOwnership(@PathVariable Long id, @RequestBody GalleryOwnership galleryOwnership) {
        if (!galleryOwnershipService.getGalleryOwnershipById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        galleryOwnership.setId(id);
        GalleryOwnership updatedOwnership = galleryOwnershipService.saveGalleryOwnership(galleryOwnership);
        return ResponseEntity.ok(updatedOwnership);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить запись о владении галереей")
    @ApiResponse(responseCode = "200", description = "Запись успешно удалена")
    public ResponseEntity<Void> deleteGalleryOwnership(@PathVariable Long id) {
        galleryOwnershipService.deleteGalleryOwnership(id);
        return ResponseEntity.ok().build();
    }
}