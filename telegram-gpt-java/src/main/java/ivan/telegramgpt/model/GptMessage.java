package ivan.telegramgpt.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class GptMessage {
    private String role;
    private String content;

    public GptMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public GptMessage() {

    }
}
