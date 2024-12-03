package org.svarian.testforkolesnik.web.rest.handler;



import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.svarian.testforkolesnik.exception.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception e, Model model) {
        model.addAttribute("errorTitle", "Внутренняя ошибка сервера");
        model.addAttribute("errorMessage", "Произошла внутренняя ошибка: " + e.getMessage());
        model.addAttribute("backLinkText", "Вернуться на главную");
        return "error/universal-error";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException e, Model model) {
        model.addAttribute("errorTitle", "Ресурс не найден");
        model.addAttribute("errorMessage", "Мы не смогли найти запрашиваемый ресурс: " + e.getMessage());
        model.addAttribute("backLinkText", "Вернуться на главную");
        return "error/universal-error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationException(MethodArgumentNotValidException e, Model model) {
        StringBuilder errorMessage = new StringBuilder("Ошибка валидации: ");
        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                errorMessage.append(fieldError.getField())
                        .append(" - ")
                        .append(fieldError.getDefaultMessage())
                        .append("; ")
        );
        model.addAttribute("errorTitle", "Ошибка валидации");
        model.addAttribute("errorMessage", errorMessage.toString());
        model.addAttribute("backLinkText", "Вернуться и попробовать снова");
        return "error/universal-error";
    }
}

