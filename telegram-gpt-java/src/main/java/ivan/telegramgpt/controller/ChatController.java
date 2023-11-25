package ivan.telegramgpt.controller;

import ivan.telegramgpt.bot.TelegramBot;
import ivan.telegramgpt.dto.ChatResponseDto;
import ivan.telegramgpt.dto.AdminMessageDto;
import ivan.telegramgpt.service.ChatService;
import ivan.telegramgpt.service.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ChatMapper chatMapper;
    private final TelegramBot telegramBot;

    @GetMapping("/history/{chatId}")
    public ResponseEntity<List<ChatResponseDto>> getHistoryByChatId(@PathVariable Long chatId) {
        return new ResponseEntity<>(chatService.getHistoryByChatId(chatId)
                .stream()
                .map(chatMapper::toDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping("/{chatId}")
    public ResponseEntity<Void> sendMessageToUser(@PathVariable Long chatId,
                                                  @RequestBody AdminMessageDto message) {
        telegramBot.sendMessage(chatId, message.getMessage());
        telegramBot.saveChat(null, message.getMessage(), chatId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
