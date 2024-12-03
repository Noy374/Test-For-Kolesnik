package org.svarian.testforkolesnik.web.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.svarian.testforkolesnik.service.DepartmentService;
import org.svarian.testforkolesnik.web.rest.dto.DepartmentDTO;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/departments")
@RequiredArgsConstructor
@Tag(name = "Department Management", description = "Управление отделами в веб-приложении")
public class DepartmentController {

    private final DepartmentService departmentService;

    private static final String REDIRECT_HOME = "redirect:/home";

    @Operation(summary = "Форма редактирования отдела", description = "Открывает форму для редактирования отдела по ID")
    @ApiResponse(responseCode = "200", description = "Форма успешно открыта")
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String editDepartmentForm(
            @Parameter(description = "ID отдела для редактирования", required = true)
            @PathVariable Long id,
            Model model) {
        DepartmentDTO departmentDTO = departmentService.findById(id);
        model.addAttribute("department", departmentDTO);
        return "departments/edit";
    }

    @Operation(summary = "Обновить отдел", description = "Сохраняет изменения отдела")
    @ApiResponse(responseCode = "302", description = "Отдел успешно обновлен, перенаправление на список отделов")
    @PostMapping("/edit")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String updateDepartment(
            @Parameter(description = "ID отдела для обновления", required = true)
            @Valid @ModelAttribute DepartmentDTO departmentDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("department", departmentDTO);
            return "departments/edit";
        }
        departmentService.save(departmentDTO);
        return REDIRECT_HOME;
    }

    @Operation(summary = "Удалить отдел", description = "Удаляет отдел по указанному ID")
    @ApiResponse(responseCode = "302", description = "Отдел успешно удален, перенаправление на список отделов")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteDepartment(
            @Parameter(description = "ID отдела для удаления", required = true)
            @PathVariable Long id) {
        departmentService.deleteById(id);
        return REDIRECT_HOME;
    }

    @Operation(summary = "Форма создания отдела", description = "Открывает форму для создания нового отдела")
    @ApiResponse(responseCode = "200", description = "Форма успешно открыта")
    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreateDepartmentForm(Model model) {
        model.addAttribute("department", new DepartmentDTO());
        return "departments/create";
    }

    @Operation(summary = "Создать отдел", description = "Создает новый отдел")
    @ApiResponse(responseCode = "302", description = "Отдел успешно создан, перенаправление на главную страницу")
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createDepartment(
            @Parameter(description = "Данные нового отдела", required = true)
            @Valid @ModelAttribute DepartmentDTO departmentDTO,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("department", new DepartmentDTO());
            return "departments/create";
        }
        departmentService.save(departmentDTO);
        return REDIRECT_HOME;
    }
}
