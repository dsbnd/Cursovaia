package artishok.controllers;

import artishok.entities.UserActivityLog;
import artishok.services.UserActivityLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userlogs")
@Tag(name = "Логи активности пользователей", description = "Просмотр логов активности пользователей")
public class UserActivityLogController {
    
    private final UserActivityLogService userActivityLogService;
    
    public UserActivityLogController(UserActivityLogService userActivityLogService) {
        this.userActivityLogService = userActivityLogService;
    }
    
    @Operation(summary = "Создание записи активности")
    @PostMapping
    public ResponseEntity<?> createLog(
            @RequestParam("userId") Long userId,
            @RequestParam("action") String action) {
        try {
            UserActivityLog log = userActivityLogService.createLog(userId, action);
            return ResponseEntity.status(HttpStatus.CREATED).body(log);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @Operation(summary = "Получение всех записей")
    @GetMapping
    public ResponseEntity<List<UserActivityLog>> getAllLogs() {
        return ResponseEntity.ok(userActivityLogService.getAllLogs());
    }
    
    @Operation(summary = "Получение записей пользователя")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserActivityLog>> getLogsByUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userActivityLogService.getLogsByUser(userId));
    }
    
    @Operation(summary = "Получение записей по действию")
    @GetMapping("/action/{action}")
    public ResponseEntity<List<UserActivityLog>> getLogsByAction(@PathVariable("action") String action) {
        return ResponseEntity.ok(userActivityLogService.getLogsByAction(action));
    }
    
    @Operation(summary = "Получение записей за период")
    @GetMapping("/date-range")
    public ResponseEntity<List<UserActivityLog>> getLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(userActivityLogService.getLogsByDateRange(startDate, endDate));
    }
    
    @Operation(summary = "Получение записей пользователя за период")
    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<UserActivityLog>> getLogsByUserAndDateRange(
            @PathVariable("userId") Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(userActivityLogService.getLogsByUserAndDateRange(userId, startDate, endDate));
    }
    
    @Operation(summary = "Получение последних 50 записей")
    @GetMapping("/last-50")
    public ResponseEntity<List<UserActivityLog>> getLast50Logs() {
        return ResponseEntity.ok(userActivityLogService.getLast50Logs());
    }
    
    @Operation(summary = "Получение записей за сегодня")
    @GetMapping("/today")
    public ResponseEntity<List<UserActivityLog>> getTodayLogs() {
        return ResponseEntity.ok(userActivityLogService.getTodayLogs());
    }
    
    @Operation(summary = "Подсчет записей пользователя")
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Map<String, Object>> countLogsByUser(@PathVariable("userId") Long userId) {
        long count = userActivityLogService.countLogsByUser(userId);
        return ResponseEntity.ok(Map.of("userId", userId, "logCount", count));
    }
    
    @Operation(summary = "Подсчет записей по действию")
    @GetMapping("/action/{action}/count")
    public ResponseEntity<Map<String, Object>> countLogsByAction(@PathVariable("action") String action) {
        long count = userActivityLogService.countLogsByAction(action);
        return ResponseEntity.ok(Map.of("action", action, "logCount", count));
    }
    
    @Operation(summary = "Общее количество записей")
    @GetMapping("/total")
    public ResponseEntity<Map<String, Object>> getTotalLogs() {
        long total = userActivityLogService.getTotalLogs();
        return ResponseEntity.ok(Map.of("totalLogs", total));
    }
    
    @Operation(summary = "Удаление старых записей")
    @DeleteMapping("/cleanup")
    public ResponseEntity<Map<String, Object>> deleteOldLogs(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cutoffDate) {
        int deleted = userActivityLogService.deleteOldLogs(cutoffDate);
        return ResponseEntity.ok(Map.of("deletedCount", deleted, "cutoffDate", cutoffDate));
    }
    
    @Operation(summary = "Логирование входа пользователя")
    @PostMapping("/log-login")
    public ResponseEntity<?> logLogin(@RequestParam("userId") Long userId) {
        try {
            userActivityLogService.logLogin(userId);
            return ResponseEntity.ok(Map.of("message", "Запись о входе создана"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @Operation(summary = "Логирование выхода пользователя")
    @PostMapping("/log-logout")
    public ResponseEntity<?> logLogout(@RequestParam("userId") Long userId) {
        try {
            userActivityLogService.logLogout(userId);
            return ResponseEntity.ok(Map.of("message", "Запись о выходе создана"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @Operation(summary = "Логирование создания бронирования")
    @PostMapping("/log-booking-creation")
    public ResponseEntity<?> logBookingCreation(@RequestParam("userId") Long userId) {
        try {
            userActivityLogService.logBookingCreation(userId);
            return ResponseEntity.ok(Map.of("message", "Запись о создании бронирования создана"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}