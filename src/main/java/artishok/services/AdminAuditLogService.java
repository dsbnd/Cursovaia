package artishok.services;

import artishok.entities.AdminAuditLog;
import artishok.entities.User;
import artishok.repositories.AdminAuditLogRepository;
import artishok.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminAuditLogService {

	private final AdminAuditLogRepository adminAuditLogRepository;
	private final UserRepository userRepository;

	public AdminAuditLogService(AdminAuditLogRepository adminAuditLogRepository, UserRepository userRepository) {
		this.adminAuditLogRepository = adminAuditLogRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public AdminAuditLog createLog(Long adminId, String action, Long targetEntityId) {
		User admin = userRepository.findById(adminId)
				.orElseThrow(() -> new IllegalArgumentException("Администратор не найден"));

		if (!admin.getRole().name().equals("ADMIN")) {
			throw new IllegalArgumentException("Только администраторы могут выполнять действия с логированием");
		}

		AdminAuditLog log = new AdminAuditLog(admin, action, targetEntityId);
		log.setTimestamp(LocalDateTime.now());

		return adminAuditLogRepository.save(log);
	}

	@Transactional
	public AdminAuditLog createLog(User admin, String action, Long targetEntityId) {
		if (!admin.getRole().name().equals("ADMIN")) {
			throw new IllegalArgumentException("Только администраторы могут выполнять действия с логированием");
		}
		AdminAuditLog log = new AdminAuditLog(admin, action, targetEntityId);
		log.setTimestamp(LocalDateTime.now());

		return adminAuditLogRepository.save(log);
	}


	@Transactional
	public void logAdminAction(Long adminId, String action, Long targetEntityId) {
		createLog(adminId, action, targetEntityId);
	}


	@Transactional
	public void logGalleryApproval(Long adminId, Long galleryId) {
		createLog(adminId, "APPROVE_GALLERY", galleryId);
	}

	@Transactional
	public void logGalleryRejection(Long adminId, Long galleryId) {
		createLog(adminId, "REJECT_GALLERY", galleryId);
	}

	@Transactional
	public void logUserBlock(Long adminId, Long userId) {
		createLog(adminId, "BLOCK_USER", userId);
	}

	@Transactional
	public void logUserUnblock(Long adminId, Long userId) {
		createLog(adminId, "UNBLOCK_USER", userId);
	}

	@Transactional
	public void logContentModeration(Long adminId, Long contentId, String actionType) {
		createLog(adminId, actionType + "_CONTENT", contentId);
	}

	public Optional<AdminAuditLog> getLogById(Long id) {
		return adminAuditLogRepository.findById(id);
	}

	public List<AdminAuditLog> getAllLogs() {
		return adminAuditLogRepository.findAll();
	}


	public List<AdminAuditLog> getLogsByAdmin(Long adminId) {
		return adminAuditLogRepository.findByAdminId(adminId);
	}


	public List<AdminAuditLog> getLogsByAction(String action) {
		return adminAuditLogRepository.findByAction(action);
	}

	public List<AdminAuditLog> getLogsByTargetEntity(Long targetEntityId) {
		return adminAuditLogRepository.findByTargetEntityId(targetEntityId);
	}

	public List<AdminAuditLog> getLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
		return adminAuditLogRepository.findByTimestampBetween(startDate, endDate);
	}

	public List<AdminAuditLog> getRecentLogs() {
		return adminAuditLogRepository.findTop100ByOrderByTimestampDesc();
	}
	
	public List<AdminAuditLog> getTodayLogs() {
		LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
		LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
		return adminAuditLogRepository.findByTimestampBetween(startOfDay, endOfDay);
	}

	public List<AdminAuditLog> getLast7DaysLogs() {
		LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
		return adminAuditLogRepository.findByTimestampAfter(weekAgo);
	}

	public List<AdminAuditLog> getLast30DaysLogs() {
		LocalDateTime monthAgo = LocalDateTime.now().minusDays(30);
		return adminAuditLogRepository.findByTimestampAfter(monthAgo);
	}

	public Map<String, Long> getAdminActionStats(Long adminId) {
		List<Object[]> stats = adminAuditLogRepository.getActionStatsByAdmin(adminId);
		return stats.stream().collect(Collectors.toMap(obj -> (String) obj[0], obj -> (Long) obj[1]));
	}

	public Map<String, Object> getAdminStats() {
		List<User> admins = userRepository.findByRole(artishok.entities.enums.UserRole.ADMIN);

		Map<String, Object> stats = new java.util.HashMap<>();
		for (User admin : admins) {
			long logCount = adminAuditLogRepository.countByAdminId(admin.getId());
			stats.put(admin.getFullName() + " (ID: " + admin.getId() + ")", logCount);
		}

		return stats;
	}


	public boolean logExists(Long id) {
		return adminAuditLogRepository.existsById(id);
	}

	@Transactional
	public int deleteOldLogs(LocalDateTime cutoffDate) {
		List<AdminAuditLog> oldLogs = adminAuditLogRepository.findByTimestampBefore(cutoffDate);
		adminAuditLogRepository.deleteAll(oldLogs);
		return oldLogs.size();
	}

	@Transactional
	public void deleteLogsByAdmin(Long adminId) {
		List<AdminAuditLog> logs = adminAuditLogRepository.findByAdminId(adminId);
		adminAuditLogRepository.deleteAll(logs);
	}

	@Transactional
	public void deleteAllLogs() {
		adminAuditLogRepository.deleteAll();
	}
}