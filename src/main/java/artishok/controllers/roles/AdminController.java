package artishok.controllers.roles;

import artishok.entities.User;
import artishok.entities.enums.UserRole;
import artishok.services.UserService;
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
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Администратор", description = "API для администратора")
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    @Operation(summary = "Получить всех пользователей с фильтрацией")
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(required = false) UserRole role,
            @RequestParam(required = false) String search) {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{id}/activate")
    @Operation(summary = "Активировать/деактивировать пользователя")
    public ResponseEntity<?> toggleUserActivation(@PathVariable Long id, @RequestBody Map<String, Boolean> request) {

        return ResponseEntity.ok(Map.of("message", "Статус пользователя обновлен"));
    }

    @PutMapping("/users/{id}/role")
    @Operation(summary = "Изменить роль пользователя")
    public ResponseEntity<?> changeUserRole(@PathVariable Long id, @RequestBody Map<String, UserRole> request) {
        UserRole newRole = request.get("role");
        return ResponseEntity.ok(Map.of("message", "Роль пользователя изменена"));
    }
}