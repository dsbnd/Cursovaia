package artishok.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import artishok.entities.Gallery;
import artishok.services.GalleryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/galleries")
public class GalleryController {
    private final GalleryService galleryService;

    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping
    @Operation(summary = "Получить все галереи")
    @ApiResponse(responseCode = "200", description = "Список галерей успешно получен")
    @ApiResponse(responseCode = "204", description = "Галереи не найдены")
    public ResponseEntity<List<Gallery>> getAllGalleries() {
        List<Gallery> galleries = galleryService.getAllGalleries();
        if (galleries.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(galleries);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить галерею по ID")
    @ApiResponse(responseCode = "200", description = "Галерея найдена")
    @ApiResponse(responseCode = "404", description = "Галерея не найдена")
    public ResponseEntity<Gallery> getGalleryById(@PathVariable Long id) {
        Optional<Gallery> gallery = galleryService.getGalleryById(id);
        return gallery.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать новую галерею")
    @ApiResponse(responseCode = "200", description = "Галерея успешно создана")
    public ResponseEntity<Gallery> createGallery(@RequestBody Gallery gallery) {
        Gallery savedGallery = galleryService.saveGallery(gallery);
        return ResponseEntity.ok(savedGallery);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить галерею")
    @ApiResponse(responseCode = "200", description = "Галерея успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Галерея не найдена")
    public ResponseEntity<Gallery> updateGallery(@PathVariable Long id, @RequestBody Gallery gallery) {
        if (!galleryService.getGalleryById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        gallery.setId(id);
        Gallery updatedGallery = galleryService.saveGallery(gallery);
        return ResponseEntity.ok(updatedGallery);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить галерею")
    @ApiResponse(responseCode = "200", description = "Галерея успешно удалена")
    public ResponseEntity<Void> deleteGallery(@PathVariable Long id) {
        galleryService.deleteGallery(id);
        return ResponseEntity.ok().build();
    }
}