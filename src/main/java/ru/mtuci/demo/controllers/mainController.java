package ru.mtuci.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class mainController {



    @GetMapping("/autorisation")
    public String autorisation(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("name", "Главная страница");
        return "autorisation";
    }

    @GetMapping("/registration")
    public String registration(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("name", "Главная страница");
        return "registration";
    }

}