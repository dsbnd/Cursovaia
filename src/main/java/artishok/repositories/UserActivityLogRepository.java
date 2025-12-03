package artishok.repositories;

import artishok.entities.UserActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, Long> {
    
    List<UserActivityLog> findByUserId(Long userId);
    
    List<UserActivityLog> findByAction(String action);
    
    List<UserActivityLog> findByActionContainingIgnoreCase(String action);
    
    List<UserActivityLog> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<UserActivityLog> findByUserIdAndTimestampBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    
    List<UserActivityLog> findByUserIdAndAction(Long userId, String action);
    
    long countByUserId(Long userId);
    
    long countByAction(String action);
    
    List<UserActivityLog> findTop50ByOrderByTimestampDesc();
    
}