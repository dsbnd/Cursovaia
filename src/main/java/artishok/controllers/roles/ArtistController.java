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
@RequestMapping("/api/artworks")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ARTIST')")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Художник", description = "API для художников")
public class ArtistController {

    @GetMapping("/my-artworks")
    @Operation(summary = "Получить мои произведения")
    public ResponseEntity<?> getMyArtworks() {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping("/")
    @Operation(summary = "Добавить новое произведение")
    public ResponseEntity<?> createArtwork(@RequestBody Map<String, Object> artworkData) {
        return ResponseEntity.ok(Map.of("message", "Произведение добавлено"));
    }

    @GetMapping("/bookings/active")
    @Operation(summary = "Получить активные бронирования")
    public ResponseEntity<?> getActiveBookings() {
        return ResponseEntity.ok(List.of());
    }
}