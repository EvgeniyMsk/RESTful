package edu.school21.restful.models.dto;

import edu.school21.restful.models.roles.ERole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
//@Schema(description = "Сущность пользователя")
public class UserDto {
//    @Schema(description = "Идентификатор", example = "A-124523")
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Set<ERole> roles;
}
