package by.tms.gradework_pm.controller;

import by.tms.gradework_pm.dto.RegistrationDto;
import by.tms.gradework_pm.entity.principle.User;
import by.tms.gradework_pm.exception.BusinessException;
import by.tms.gradework_pm.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping({"/","login"})
    public String loginPage() {
        return "security/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "security/register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult result,
                           Model model) {

        User existingUser = userService.findUserByEmail(user.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "There is already a user with same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "security/register";
        }

        try {
            userService.saveUser(user);
        } catch (BusinessException e) {
            return "errorpages/error";
        }
        return "redirect:/security/register?success";
    }
}
