package ivan.telegramgpt.dto.gpt;

import ivan.telegramgpt.model.GptMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class GptRequestDto {
    private String model;
    private List<GptMessage> messages;
}
