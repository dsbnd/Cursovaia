package artishok.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import artishok.entities.AdminAuditLog;

@Repository
public interface AdminAuditRepository extends JpaRepository<AdminAuditLog, Long> {
	// Для администратора: просмотр логов (прецедент 3.1.1.6)
	@Query("SELECT l FROM AdminAuditLog l WHERE " + "(:adminId IS NULL OR l.admin.id = :adminId) "
			+ "AND (:action IS NULL OR l.action = :action) " + "AND (:startDate IS NULL OR l.timestamp >= :startDate) "
			+ "AND (:endDate IS NULL OR l.timestamp <= :endDate) " + "ORDER BY l.timestamp DESC")
	List<AdminAuditLog> findLogsByFilters(@Param("adminId") Long adminId, @Param("action") String action,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

	// Статистика действий администратора
	@Query("SELECT l.action, COUNT(l) FROM AdminAuditLog l " + "WHERE l.timestamp >= :startDate " + "GROUP BY l.action "
			+ "ORDER BY COUNT(l) DESC")
	List<Object[]> getActionStatistics(@Param("startDate") LocalDateTime startDate);

	// Логи по конкретной сущности
	List<AdminAuditLog> findByTargetEntityId(Long targetEntityId);
}
