package ru.mtuci.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class mainController {



    @GetMapping("/autorisation") // указываем наш юрл адрес, делаем на главную страничку
    // пока работаем с боллее простой версией
    // вызываем html шаблон home
    public String autorisation(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("name", "Главная страница"); // "title" передаётся в шаблон и в нём, при обращении, вывыдется "Главная старница"
        return "autorisation";
    }

    @GetMapping("/registration") // указываем наш юрл адрес, делаем на главную страничку
    // пока работаем с боллее простой версией
    // вызываем html шаблон home
    public String registration(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("name", "Главная страница"); // "title" передаётся в шаблон и в нём, при обращении, вывыдется "Главная старница"
        return "registration";
    }

}