package edu.school21.restful.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Сущность пользователя")
public class UserDto {
    @Schema(description = "Идентификатор", example = "A-124523")
    private String username;
    private String password;
}
