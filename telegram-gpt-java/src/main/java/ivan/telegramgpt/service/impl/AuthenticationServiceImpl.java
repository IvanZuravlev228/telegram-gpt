package ivan.telegramgpt.service.impl;

import ivan.telegramgpt.model.entity.User;
import ivan.telegramgpt.reposiroty.UserRepository;
import ivan.telegramgpt.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        user.setRole(User.Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) throws RuntimeException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new NoSuchElementException("Can't find user by username: " + username));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect username or password");
        }
        return user;
    }
}
