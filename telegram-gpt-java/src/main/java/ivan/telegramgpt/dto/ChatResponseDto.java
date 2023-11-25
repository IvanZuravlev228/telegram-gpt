package ivan.telegramgpt.dto;

import lombok.Data;

@Data
public class ChatResponseDto {
    private Long id;
    private String userMessage;
    private String gptAnswer;
    private Long chatId;
}
