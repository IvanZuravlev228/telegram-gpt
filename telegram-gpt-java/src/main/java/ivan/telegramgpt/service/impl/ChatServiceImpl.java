package ivan.telegramgpt.service.impl;

import java.util.List;
import ivan.telegramgpt.model.entity.Chat;
import ivan.telegramgpt.reposiroty.ChatRepository;
import ivan.telegramgpt.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    @Override
    public List<Chat> getHistoryByChatId(Long chatId) {
        return chatRepository.findAllByChatId(chatId);
    }
}
