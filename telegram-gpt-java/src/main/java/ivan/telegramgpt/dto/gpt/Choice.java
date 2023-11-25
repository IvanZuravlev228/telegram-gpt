package ivan.telegramgpt.dto.gpt;

import ivan.telegramgpt.model.GptMessage;
import lombok.Data;

@Data
public class Choice {
    private int index;
    private GptMessage message;
}
