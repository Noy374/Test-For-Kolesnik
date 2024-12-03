package org.svarian.testforkolesnik.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Блок содежит информацию о статусе ответа от бэкенда")
public class CommonResponseDTO {

    @Schema(description = "Код ошибки", example = "1022")
    private String code;

    @Schema(description = "Текста ошибки", example = "Сервис временно недоступен")
    private String message;
}
