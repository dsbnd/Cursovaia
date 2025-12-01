package artishok.services;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import artishok.entities.User;
import artishok.entities.enums.UserRole;
import artishok.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	private final UserRepository userRepository;
	private static final Logger log = LoggerFactory.getLogger(UserService.class); 

	UserService(UserRepository userRepository) {

		this.userRepository = userRepository;
	}

	public List<User> getAllUsers(UserRole role, String search) {
        log.info("Получение списка пользователей: роль={}, поиск={}", role, search);
        return userRepository.findUsersByFilters(role, search);
    }
	
//	@Transactional
//    public User registerUser(UserRegistrationDto registrationDto) {
//        log.info("Регистрация пользователя: {}", registrationDto.getEmail());
//        
//        // Проверка уникальности email
//        if (userRepository.existsByEmail(registrationDto.getEmail())) {
//            throw new RuntimeException("Email уже используется");
//        }
//        
//        // Создание пользователя
//        User user = new User();
//        user.setEmail(registrationDto.getEmail());
//        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
//        user.setFullName(registrationDto.getFullName());
//        user.setRole(registrationDto.getRole());
//        user.setPhoneNumber(registrationDto.getPhoneNumber());
//        user.setRegistrationDate(LocalDateTime.now());
//        user.setIsActive(true);
//        
//        User savedUser = userRepository.save(user);
//        
//        // Логирование активности
//        logUserActivity(savedUser.getId(), "REGISTRATION", 
//                       "Пользователь зарегистрирован");
//        
//        log.info("Пользователь успешно зарегистрирован: ID={}", savedUser.getId());
//        return savedUser;
//    }

}
