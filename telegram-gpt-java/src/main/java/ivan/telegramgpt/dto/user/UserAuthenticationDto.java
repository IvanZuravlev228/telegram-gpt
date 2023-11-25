package ivan.telegramgpt.dto.user;

import lombok.Data;

@Data
public class UserAuthenticationDto {
    private String username;
    private String password;
}
