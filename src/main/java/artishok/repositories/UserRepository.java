package artishok.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import artishok.entities.User;
import artishok.entities.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	List<User> findByRole(UserRole role);

	boolean existsByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);

	List<User> findByIsActiveTrue();
	
	// Для администратора: поиск по ролям с пагинацией
    @Query("SELECT u FROM User u WHERE (:role IS NULL OR u.role = :role) " +
           "AND (:search IS NULL OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<User> findUsersByFilters(@Param("role") UserRole role, 
                                  @Param("search") String search);
    
}
