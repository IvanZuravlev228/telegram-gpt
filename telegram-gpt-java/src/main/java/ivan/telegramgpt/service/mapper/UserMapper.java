package ivan.telegramgpt.service.mapper;

import ivan.telegramgpt.config.MapperConfig;
import ivan.telegramgpt.dto.user.UserResponseDto;
import ivan.telegramgpt.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);
}