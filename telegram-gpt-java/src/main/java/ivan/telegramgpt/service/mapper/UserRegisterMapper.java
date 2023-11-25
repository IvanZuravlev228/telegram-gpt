package ivan.telegramgpt.service.mapper;

import ivan.telegramgpt.config.MapperConfig;
import ivan.telegramgpt.dto.user.UserAuthenticationDto;
import ivan.telegramgpt.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserRegisterMapper {
    User toModel(UserAuthenticationDto dto);
}