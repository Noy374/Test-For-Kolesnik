package org.svarian.testforkolesnik.web.rest.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO для отображения данных на домашней странице")
public class HomePageDTO {

    @Schema(description = "Информация о текущем пользователе")
    private UserDTO user;

    @Schema(description = "Роли текущего пользователя", example = "[\"ADMIN\", \"MANAGER\"]")
    private List<String> userRoles;

    @Schema(description = "Список коллег пользователя из того же отдела")
    private List<UserDTO> colleagues;

    @Schema(description = "Список всех сотрудников для администратора")
    private List<UserDTO> allEmployees;

    @Schema(description = "Информация об отделе пользователя")
    private DepartmentDTO department;

    @Schema(description = "Список всех отделов (только для администратора)")
    private List<DepartmentDTO> departments;

    @Schema(description = "Список сотрудников текущего отдела (для менеджера)")
    private List<UserDTO> departmentEmployees;

    public Map<String, Object> toModelAttributes() {
        return Map.of(
                "user", user,
                "userRoles", userRoles,
                "colleagues", colleagues != null ? colleagues : List.of(),
                "allEmployees", allEmployees != null ? allEmployees : List.of(),
                "department", department!=null ? department : new DepartmentDTO(),
                "departments", departments != null ? departments : List.of(),
                "departmentEmployees", departmentEmployees != null ? departmentEmployees : List.of()
        );
    }
}

