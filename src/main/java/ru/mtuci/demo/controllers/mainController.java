package ru.mtuci.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mtuci.demo.exceptions.UserAlreadyExistException;
import ru.mtuci.demo.models.UserData;
import ru.mtuci.demo.service.UserRegistrationService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class mainController {


    @GetMapping("/autorisation")
    public String autorisation(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        return "autorisation";
    }

    @Autowired
    private UserRegistrationService userRegistrationService;

    @GetMapping("/registration")
    public String registration(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("userData", new UserData());
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegistration(final @Valid UserData userData, final BindingResult bindingResult, final Model model){
        String password = userData.getPassword();
        Pattern uppercasePattern = Pattern.compile("[A-Z]");
        Matcher uppercaseMatcher = uppercasePattern.matcher(password);
        if (password.length() < 8 || !uppercaseMatcher.find()) {
            model.addAttribute("registrationError", "Пароль должен быть длиной не менее 8 символов и соддержать символы верхнего и нижнего регистра!");
            return "registration";
        }

        try {
            userRegistrationService.registration(userData);
        }catch (UserAlreadyExistException e){
            model.addAttribute("registrationError", "Учетная запись для этого адреса электронной почты уже существует.");
            return "registration";
        }
        return "redirect:/main";
    }
}