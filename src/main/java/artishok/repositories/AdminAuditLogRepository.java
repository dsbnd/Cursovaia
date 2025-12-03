package artishok.repositories;

import artishok.entities.AdminAuditLog;
import artishok.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdminAuditLogRepository extends JpaRepository<AdminAuditLog, Long> {
    
    List<AdminAuditLog> findByAdmin(User admin);
    
    List<AdminAuditLog> findByAdminId(Long adminId);

    List<AdminAuditLog> findByAction(String action);

    List<AdminAuditLog> findByActionContainingIgnoreCase(String action);
    
    List<AdminAuditLog> findByTargetEntityId(Long targetEntityId);
    
    List<AdminAuditLog> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);
 
    List<AdminAuditLog> findByTimestampAfter(LocalDateTime date);
    
    List<AdminAuditLog> findByTimestampBefore(LocalDateTime date);

    List<AdminAuditLog> findTop100ByOrderByTimestampDesc();
    
    List<AdminAuditLog> findByAdminIdAndAction(Long adminId, String action);
    
    List<AdminAuditLog> findByAdminIdAndTimestampBetween(Long adminId, LocalDateTime startDate, LocalDateTime endDate);
    
    List<AdminAuditLog> findByActionAndTimestampBetween(String action, LocalDateTime startDate, LocalDateTime endDate);
    
    
    @Query("SELECT l.action, COUNT(l) FROM AdminAuditLog l WHERE l.admin.id = :adminId GROUP BY l.action")
    List<Object[]> getActionStatsByAdmin(@Param("adminId") Long adminId);
    
    @Query("SELECT l.action, COUNT(l) as count FROM AdminAuditLog l GROUP BY l.action ORDER BY count DESC")
    List<Object[]> getMostFrequentActions();
    
    long countByAdminId(Long adminId);
    
    long countByAction(String action);
}