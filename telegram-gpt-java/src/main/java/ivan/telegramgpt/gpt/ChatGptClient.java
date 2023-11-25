package ivan.telegramgpt.gpt;

import java.util.ArrayList;
import java.util.List;
import ivan.telegramgpt.dto.gpt.GptRequestDto;
import ivan.telegramgpt.dto.gpt.GptResponseDto;
import ivan.telegramgpt.model.entity.Chat;
import ivan.telegramgpt.model.GptMessage;
import ivan.telegramgpt.service.ChatService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ChatGptClient {
    @Qualifier("openaiRestTemplate")
    private final RestTemplate restTemplate;
    private final ChatService chatService;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;


    public ChatGptClient(RestTemplate restTemplate, ChatService chatService) {
        this.restTemplate = restTemplate;
        this.chatService = chatService;
    }

    public String sendQuestion(String prompt, Long chatId) {
        GptRequestDto request = new GptRequestDto(model, setMessage(prompt, chatId));

        GptResponseDto response = restTemplate.postForObject(
                apiUrl,
                request,
                GptResponseDto.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        return response.getChoices().get(0).getMessage().getContent();
    }

    private List<GptMessage> setMessage(String prompt, Long chatId) {
        List<GptMessage> messages = new ArrayList<>();
        List<Chat> history = chatService.getHistoryByChatId(chatId);
        for (Chat chat : history) {
            messages.add(new GptMessage("assistant", chat.getGptAnswer()));
            if (chat.getUserMessage() != null) {
                messages.add(new GptMessage("user", chat.getUserMessage()));
            }
        }
        messages.add(new GptMessage("user", prompt));
        return messages;
    }
}
