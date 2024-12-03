package org.svarian.testforkolesnik.web.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.svarian.testforkolesnik.service.DepartmentService;
import org.svarian.testforkolesnik.service.UserService;



import org.springframework.web.bind.annotation.*;
import org.svarian.testforkolesnik.web.rest.dto.UserDTO;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Управление пользователями в веб-приложении")
public class UserController {

    private final UserService userService;
    private final DepartmentService departmentService;

    private static final String REDIRECT_HOME = "redirect:/home";

    private void populateModelWithRolesAndDepartments(Model model) {
        model.addAttribute("roles", userService.getAllRoles());
        model.addAttribute("departments", departmentService.findAll());
    }

    @Operation(summary = "Форма создания пользователя", description = "Открывает форму для создания нового пользователя")
    @ApiResponse(responseCode = "200", description = "Форма успешно открыта")
    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        populateModelWithRolesAndDepartments(model);
        return "users/create";
    }

    @Operation(summary = "Создать пользователя", description = "Создает нового пользователя с указанными ролями и отделом")
    @ApiResponse(responseCode = "302", description = "Пользователь успешно создан, перенаправление на главную страницу")
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createUser(
            @Parameter(description = "Данные пользователя", required = true)
            @Valid @ModelAttribute UserDTO userDTO,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("user", new UserDTO());
            populateModelWithRolesAndDepartments(model);
            return "users/create";
        }

        userService.createUser(userDTO);
        return REDIRECT_HOME;
    }

    @Operation(summary = "Форма редактирования пользователя", description = "Открывает форму редактирования для указанного пользователя")
    @ApiResponse(responseCode = "200", description = "Форма успешно открыта")
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public String editUserForm(
            @Parameter(description = "ID пользователя для редактирования", required = true)
            @PathVariable Long id,
            Model model) {

        UserDTO userDTO = userService.getUserById(id);
        model.addAttribute("user", userDTO);
        populateModelWithRolesAndDepartments(model);
        return "users/edit";
    }

    @Operation(summary = "Обновить пользователя", description = "Обновляет данные указанного пользователя")
    @ApiResponse(responseCode = "302", description = "Пользователь успешно обновлен, перенаправление на список пользователей")
    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public String updateUser(
            @Parameter(description = "ID пользователя для обновления", required = true)
            @PathVariable Long id,
            @Valid @ModelAttribute UserDTO userDTO,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("user", userDTO);
            populateModelWithRolesAndDepartments(model);
            return "users/edit";
        }

        userService.updateUser(id, userDTO);
        return REDIRECT_HOME;
    }

    @Operation(summary = "Удалить пользователя", description = "Удаляет указанного пользователя")
    @ApiResponse(responseCode = "302", description = "Пользователь успешно удален, перенаправление на главную страницу")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public String deleteUser(
            @Parameter(description = "ID пользователя для удаления", required = true)
            @PathVariable Long id) {

        userService.deleteUser(id);
        return REDIRECT_HOME;
    }
}
