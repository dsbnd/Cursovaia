package artishok.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import artishok.entities.AdminAuditLog;
import artishok.services.AdminAuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/adminlogs")
public class AdminAuditController {
	private final AdminAuditLogService adminAuditLogService;

	AdminAuditController(AdminAuditLogService adminAuditLogService) {
		this.adminAuditLogService = adminAuditLogService;
	}

	@Operation(summary = "Создание записи в логе", description = "Ручное создание записи о действии администратора")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Запись успешно создана"),
			@ApiResponse(responseCode = "400", description = "Неверные входные данные"),
			@ApiResponse(responseCode = "404", description = "Администратор не найден") })
	@PostMapping
	public ResponseEntity<?> createLog(@RequestParam("adminId") Long adminId, @RequestParam("action") String action,
			@RequestParam(value = "targetEntityId", required = false) Long targetEntityId) {
		try {
			AdminAuditLog log = adminAuditLogService.createLog(adminId, action, targetEntityId);
			return ResponseEntity.status(HttpStatus.CREATED).body(log);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}

	@Operation(summary = "Получение записи по ID", description = "Получение информации о записи лога по её идентификатору")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Запись найдена"),
			@ApiResponse(responseCode = "404", description = "Запись не найдена") })
	@GetMapping("/{id}")
	public ResponseEntity<?> getLogById(@PathVariable("id") Long id) {
		Optional<AdminAuditLog> opt_log = adminAuditLogService.getLogById(id);
		if (opt_log.isPresent()) {
			AdminAuditLog adminAuditLog = opt_log.get();
			return ResponseEntity.ok(adminAuditLog);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Запись лога не найдена"));
		}
	}

	@Operation(summary = "Получение всех записей", description = "Получение списка всех записей логов администраторов")
	@ApiResponse(responseCode = "200", description = "Список записей получен")
	@GetMapping
	public ResponseEntity<List<AdminAuditLog>> getAllLogs() {
		List<AdminAuditLog> logs = adminAuditLogService.getAllLogs();
		return ResponseEntity.ok(logs);
	}

	@Operation(summary = "Получение записей по администратору", description = "Получение всех записей логов конкретного администратора")
	@ApiResponse(responseCode = "200", description = "Список записей получен")
	@GetMapping("/admin/{adminId}")
	public ResponseEntity<List<AdminAuditLog>> getLogsByAdmin(@PathVariable("adminId") Long adminId) {
		List<AdminAuditLog> logs = adminAuditLogService.getLogsByAdmin(adminId);
		return ResponseEntity.ok(logs);
	}

	@Operation(summary = "Получение записей по действию", description = "Фильтрация записей логов по типу действия")
	@ApiResponse(responseCode = "200", description = "Список записей получен")
	@GetMapping("/action/{action}")
	public ResponseEntity<List<AdminAuditLog>> getLogsByAction(@PathVariable("action") String action) {
		List<AdminAuditLog> logs = adminAuditLogService.getLogsByAction(action);
		return ResponseEntity.ok(logs);
	}

	@Operation(summary = "Получение записей по целевой сущности", description = "Получение всех записей логов для конкретной сущности")
	@ApiResponse(responseCode = "200", description = "Список записей получен")
	@GetMapping("/target/{targetEntityId}")
	public ResponseEntity<List<AdminAuditLog>> getLogsByTargetEntity(
			@PathVariable("targetEntityId") Long targetEntityId) {
		List<AdminAuditLog> logs = adminAuditLogService.getLogsByTargetEntity(targetEntityId);
		return ResponseEntity.ok(logs);
	}

	@Operation(summary = "Получение записей по временному диапазону", description = "Получение записей логов за указанный период времени")
	@ApiResponse(responseCode = "200", description = "Список записей получен")
	@GetMapping("/date-range")
	public ResponseEntity<List<AdminAuditLog>> getLogsByDateRange(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
		List<AdminAuditLog> logs = adminAuditLogService.getLogsByDateRange(startDate, endDate);
		return ResponseEntity.ok(logs);
	}

	@Operation(summary = "Получение последних записей", description = "Получение 100 последних записей логов")
	@ApiResponse(responseCode = "200", description = "Список последних записей получен")
	@GetMapping("/recent")
	public ResponseEntity<List<AdminAuditLog>> getRecentLogs() {
		List<AdminAuditLog> logs = adminAuditLogService.getRecentLogs();
		return ResponseEntity.ok(logs);
	}

	@Operation(summary = "Получение записей за сегодня", description = "Получение всех записей логов за текущий день")
	@ApiResponse(responseCode = "200", description = "Список записей за сегодня получен")
	@GetMapping("/today")
	public ResponseEntity<List<AdminAuditLog>> getTodayLogs() {
		List<AdminAuditLog> logs = adminAuditLogService.getTodayLogs();
		return ResponseEntity.ok(logs);
	}

	@Operation(summary = "Получение записей за последние 7 дней", description = "Получение записей логов за последнюю неделю")
	@ApiResponse(responseCode = "200", description = "Список записей получен")
	@GetMapping("/last-7-days")
	public ResponseEntity<List<AdminAuditLog>> getLast7DaysLogs() {
		List<AdminAuditLog> logs = adminAuditLogService.getLast7DaysLogs();
		return ResponseEntity.ok(logs);
	}

	@Operation(summary = "Получение записей за последние 30 дней", description = "Получение записей логов за последний месяц")
	@ApiResponse(responseCode = "200", description = "Список записей получен")
	@GetMapping("/last-30-days")
	public ResponseEntity<List<AdminAuditLog>> getLast30DaysLogs() {
		List<AdminAuditLog> logs = adminAuditLogService.getLast30DaysLogs();
		return ResponseEntity.ok(logs);
	}

	@Operation(summary = "Получение статистики действий администратора", description = "Получение статистики по действиям конкретного администратора")
	@ApiResponse(responseCode = "200", description = "Статистика получена")
	@GetMapping("/admin/{adminId}/stats")
	public ResponseEntity<Map<String, Long>> getAdminActionStats(@PathVariable("adminId") Long adminId) {
		Map<String, Long> stats = adminAuditLogService.getAdminActionStats(adminId);
		return ResponseEntity.ok(stats);
	}

	@Operation(summary = "Получение статистики по администраторам", description = "Получение статистики активности всех администраторов")
	@ApiResponse(responseCode = "200", description = "Статистика получена")
	@GetMapping("/admin-stats")
	public ResponseEntity<Map<String, Object>> getAdminStats() {
		Map<String, Object> stats = adminAuditLogService.getAdminStats();
		return ResponseEntity.ok(stats);
	}

	@Operation(summary = "Удаление старых записей", description = "Удаление записей логов старше указанной даты")
	@ApiResponse(responseCode = "200", description = "Записи удалены")
	@DeleteMapping("/cleanup")
	public ResponseEntity<Map<String, Object>> deleteOldLogs(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cutoffDate) {
		int deletedCount = adminAuditLogService.deleteOldLogs(cutoffDate);
		return ResponseEntity.ok(Map.of("cutoffDate", cutoffDate, "deletedCount", deletedCount, "message",
				"Удалено " + deletedCount + " записей старше " + cutoffDate));
	}

	@Operation(summary = "Очистка всех записей", description = "Полное удаление всех записей логов администраторов")
	@ApiResponse(responseCode = "204", description = "Все записи удалены")
	@DeleteMapping
	public ResponseEntity<Map<String, Object>> deleteAllLogs() {
		adminAuditLogService.deleteAllLogs();
		return ResponseEntity.ok(Map.of("message", "Все записи логов администраторов были удалены"));
	}

	@Operation(summary = "Логирование подтверждения галереи", description = "Создание записи о подтверждении галереи администратором")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Запись создана"),
			@ApiResponse(responseCode = "400", description = "Неверные данные") })
	@PostMapping("/log-gallery-approval")
	public ResponseEntity<?> logGalleryApproval(@RequestParam("adminId") Long adminId,
			@RequestParam("galleryId") Long galleryId) {
		try {
			adminAuditLogService.logGalleryApproval(adminId, galleryId);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(Map.of("message", "Запись о подтверждении галереи создана"));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}

	@Operation(summary = "Логирование отклонения галереи", description = "Создание записи об отклонении галереи администратором")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Запись создана"),
			@ApiResponse(responseCode = "400", description = "Неверные данные") })
	@PostMapping("/log-gallery-rejection")
	public ResponseEntity<?> logGalleryRejection(
			@RequestParam("adminId") Long adminId,
			@RequestParam("galleryId") Long galleryId) {
		try {
			adminAuditLogService.logGalleryRejection(adminId, galleryId);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(Map.of("message", "Запись об отклонении галереи создана"));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}

}
