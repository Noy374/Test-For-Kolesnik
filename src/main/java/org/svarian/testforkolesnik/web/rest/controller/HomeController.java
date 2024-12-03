package org.svarian.testforkolesnik.web.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.svarian.testforkolesnik.service.HomeService;
import org.svarian.testforkolesnik.web.rest.dto.HomePageDTO;

@Controller
@RequiredArgsConstructor
@Tag(name = "Home Management", description = "Обработка домашней страницы")
public class HomeController {

    private final HomeService homeService;

    @Operation(summary = "Отобразить домашнюю страницу", description = "Отображает домашнюю страницу с данными пользователя и связанными данными.")
    @ApiResponse(responseCode = "200", description = "Домашняя страница успешно отображена")
    @ApiResponse(responseCode = "302", description = "Пользователь перенаправлен на страницу входа")
    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        HomePageDTO homePageDTO = homeService.buildHomePage(principal.getName());
        System.out.println(homePageDTO);

        System.out.println(homePageDTO.toModelAttributes());
        model.addAllAttributes(homePageDTO.toModelAttributes());

        return "home";
    }
}
