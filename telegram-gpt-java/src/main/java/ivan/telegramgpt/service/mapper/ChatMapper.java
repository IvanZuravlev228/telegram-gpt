package ivan.telegramgpt.service.mapper;

import ivan.telegramgpt.config.MapperConfig;
import ivan.telegramgpt.dto.ChatResponseDto;
import ivan.telegramgpt.model.entity.Chat;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ChatMapper {
    ChatResponseDto toDto(Chat chat);
}
