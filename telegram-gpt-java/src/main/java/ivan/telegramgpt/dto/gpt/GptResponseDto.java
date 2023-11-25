package ivan.telegramgpt.dto.gpt;

import lombok.Data;
import java.util.List;

@Data
public class GptResponseDto {
    private List<Choice> choices;
}
