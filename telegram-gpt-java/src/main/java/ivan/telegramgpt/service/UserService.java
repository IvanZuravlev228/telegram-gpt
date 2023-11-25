package ivan.telegramgpt.service;

import java.util.List;
import ivan.telegramgpt.model.entity.User;

public interface UserService {
    List<User> getAll();

    User save(User user);

    User getByUsername(String username);

    void delete(Long userId);
}
