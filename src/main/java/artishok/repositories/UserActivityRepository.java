package artishok.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import artishok.entities.UserActivityLog;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivityLog, Long> {
	// Логи активности пользователя
	List<UserActivityLog> findByUserId(Long userId);

	// Для аналитики: популярные действия
	@Query("SELECT l.action, COUNT(l) FROM UserActivityLog l " + "WHERE l.timestamp >= :startDate "
			+ "GROUP BY l.action " + "ORDER BY COUNT(l) DESC")
	List<Object[]> getPopularActions(@Param("startDate") LocalDateTime startDate);

	// Активность за период
	@Query("SELECT l FROM UserActivityLog l WHERE " + "l.user.id = :userId "
			+ "AND l.timestamp BETWEEN :startDate AND :endDate " + "ORDER BY l.timestamp DESC")
	List<UserActivityLog> getUserActivityPeriod(@Param("userId") Long userId,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

	// Последняя активность пользователя
	@Query("SELECT l FROM UserActivityLog l WHERE l.user.id = :userId " + "ORDER BY l.timestamp DESC LIMIT 1")
	Optional<UserActivityLog> findLastUserActivity(@Param("userId") Long userId);
}
