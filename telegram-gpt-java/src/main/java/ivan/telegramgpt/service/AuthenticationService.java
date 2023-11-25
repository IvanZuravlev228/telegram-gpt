package ivan.telegramgpt.service;

import ivan.telegramgpt.model.entity.User;

public interface AuthenticationService {
    User register(User user);

    User login(String login, String password) throws RuntimeException;
}
