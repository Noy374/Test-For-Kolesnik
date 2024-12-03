package org.svarian.testforkolesnik.web.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.svarian.testforkolesnik.annotation.UniqueUsername;
import org.svarian.testforkolesnik.entity.Role;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO для создания и обновления пользователя")
public class UserDTO {

    private Long id;

    @Schema(description = "Имя пользователя", example = "johndoe")
    @NotNull(message = "Имя пользователя не может быть пустым")
    @UniqueUsername
    private String username;

    @Schema(description = "Пароль пользователя", example = "password123")
    @NotNull(message = "Пароль не может быть пустым")
    private String password;

    @Schema(description = "Роли пользователя")
    @NotNull(message = "Роли пользователя не могут быть пустыми")
    private Set<Role> roles;

    @Schema(description = "ID отдела, к которому принадлежит пользователь", example = "1")
    @NotNull(message = "ID отдела не может быть пустым")
    private Long departmentId;
}

