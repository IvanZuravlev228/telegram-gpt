package ivan.telegramgpt.reposiroty;

import java.util.List;
import java.util.Optional;
import ivan.telegramgpt.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findAllByRole(User.Role role);
}
