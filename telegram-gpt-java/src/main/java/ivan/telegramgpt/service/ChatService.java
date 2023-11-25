package ivan.telegramgpt.service;

import java.util.List;
import ivan.telegramgpt.model.entity.Chat;

public interface ChatService {
    void save(Chat chat);

    List<Chat> getHistoryByChatId(Long chatId);
}
