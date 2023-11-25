package ivan.telegramgpt.reposiroty;

import java.util.List;
import ivan.telegramgpt.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByChatId(Long chatId);
}
