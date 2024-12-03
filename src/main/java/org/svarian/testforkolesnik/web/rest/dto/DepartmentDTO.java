package org.svarian.testforkolesnik.web.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;
import org.svarian.testforkolesnik.annotation.UniqueDepartmentName;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO для создания и обновления отдела")
public class DepartmentDTO {

    private Long id;

    @Schema(description = "Название отдела", example = "Маркетинг")
    @NotNull(message = "Название отдела не может быть пустым")
    @Size(min = 1, max = 100, message = "Название отдела должно быть от 1 до 100 символов")
    @UniqueDepartmentName
    private String name;

}
