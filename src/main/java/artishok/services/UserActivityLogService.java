package artishok.services;

import artishok.entities.UserActivityLog;
import artishok.entities.User;
import artishok.repositories.UserActivityLogRepository;
import artishok.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserActivityLogService {
    
    private final UserActivityLogRepository userActivityLogRepository;
    private final UserRepository userRepository;
    
    public UserActivityLogService(UserActivityLogRepository userActivityLogRepository,
                                 UserRepository userRepository) {
        this.userActivityLogRepository = userActivityLogRepository;
        this.userRepository = userRepository;
    }
    
    @Transactional
    public UserActivityLog createLog(Long userId, String action) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        
        UserActivityLog log = new UserActivityLog(user, action);
        return userActivityLogRepository.save(log);
    }
    
    @Transactional
    public UserActivityLog createLog(User user, String action) {
        UserActivityLog log = new UserActivityLog(user, action);
        return userActivityLogRepository.save(log);
    }
    
    @Transactional
    public void logUserAction(Long userId, String action) {
        createLog(userId, action);
    }
    
    @Transactional
    public void logLogin(Long userId) {
        createLog(userId, "LOGIN");
    }
    
    @Transactional
    public void logLogout(Long userId) {
        createLog(userId, "LOGOUT");
    }
    
    @Transactional
    public void logProfileUpdate(Long userId) {
        createLog(userId, "UPDATE_PROFILE");
    }
    
    @Transactional
    public void logBookingCreation(Long userId) {
        createLog(userId, "CREATE_BOOKING");
    }
    
    @Transactional
    public void logArtworkCreation(Long userId) {
        createLog(userId, "CREATE_ARTWORK");
    }
    
    public List<UserActivityLog> getAllLogs() {
        return userActivityLogRepository.findAll();
    }
    
    public Page<UserActivityLog> getAllLogs(Pageable pageable) {
        return userActivityLogRepository.findAll(pageable);
    }
    
    
    public List<UserActivityLog> getLogsByUser(Long userId) {
        return userActivityLogRepository.findByUserId(userId);
    }
    
    public List<UserActivityLog> getLogsByAction(String action) {
        return userActivityLogRepository.findByAction(action);
    }
    
    public List<UserActivityLog> getLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return userActivityLogRepository.findByTimestampBetween(startDate, endDate);
    }
    
    public List<UserActivityLog> getLogsByUserAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return userActivityLogRepository.findByUserIdAndTimestampBetween(userId, startDate, endDate);
    }
    
    public List<UserActivityLog> getLast50Logs() {
        return userActivityLogRepository.findTop50ByOrderByTimestampDesc();
    }
    
    public List<UserActivityLog> getTodayLogs() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return userActivityLogRepository.findByTimestampBetween(startOfDay, endOfDay);
    }
    
    public long countLogsByUser(Long userId) {
        return userActivityLogRepository.countByUserId(userId);
    }
    
    public long countLogsByAction(String action) {
        return userActivityLogRepository.countByAction(action);
    }
    
    public long getTotalLogs() {
        return userActivityLogRepository.count();
    }
    
    @Transactional
    public int deleteOldLogs(LocalDateTime cutoffDate) {
        List<UserActivityLog> oldLogs = userActivityLogRepository.findByTimestampBetween(
            LocalDateTime.MIN, cutoffDate
        );
        userActivityLogRepository.deleteAll(oldLogs);
        return oldLogs.size();
    }
}