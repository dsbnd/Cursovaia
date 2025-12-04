package artishok.controllers.roles;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/galleries/manage")
@RequiredArgsConstructor
@PreAuthorize("hasRole('GALLERY_OWNER')")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Владелец галереи", description = "API для владельцев галерей")
public class GalleryOwnerController {

    @GetMapping("/my-galleries")
    @Operation(summary = "Получить мои галереи")
    public ResponseEntity<?> getMyGalleries() {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping("/exhibitions")
    @Operation(summary = "Создать выставку")
    public ResponseEntity<?> createExhibition(@RequestBody Map<String, Object> exhibitionData) {
        return ResponseEntity.ok(Map.of("message", "Выставка создана"));
    }

    @GetMapping("/bookings/pending")
    @Operation(summary = "Получить ожидающие бронирования")
    public ResponseEntity<?> getPendingBookings() {
        return ResponseEntity.ok(List.of());
    }
}