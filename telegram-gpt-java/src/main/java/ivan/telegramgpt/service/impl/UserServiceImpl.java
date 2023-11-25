package ivan.telegramgpt.service.impl;

import java.util.List;
import ivan.telegramgpt.model.entity.User;
import ivan.telegramgpt.reposiroty.UserRepository;
import ivan.telegramgpt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAllByRole(User.Role.USER);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
