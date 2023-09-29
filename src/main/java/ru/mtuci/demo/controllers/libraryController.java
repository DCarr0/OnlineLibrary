package ru.mtuci.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class libraryController {
    @GetMapping("/main") // указываем наш юрл адрес, делаем на главную страничку
    // пока работаем с боллее простой версией
    // вызываем html шаблон home
    public String main(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("name", "Главная страница"); // "title" передаётся в шаблон и в нём, при обращении, вывыдется "Главная старница"
        return "main";
    }
}
