package ru.ci_trainee.authms.service.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ci_trainee.authms.exception.exception.UserNotFoundException;
import ru.ci_trainee.authms.model.User;
import ru.ci_trainee.authms.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User create(User user) {
        user.setIsActive(true);

        return userRepository.save(user);
    }

    @Transactional
    public void updateLastLogin(UUID userId) {
        var user = getUser(userId);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }
}
