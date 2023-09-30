package ru.mtuci.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.demo.models.Publication;
import ru.mtuci.demo.repository.PublicationRepository;

@Controller
public class libraryController {

    @Autowired
    private PublicationRepository publicationRepository;


//    @PostMapping(path="/add") // Map ONLY POST Requests
//    public @ResponseBody String addNewPublication (@RequestParam String title
//            , @RequestParam String genre) {
//        // @ResponseBody means the returned String is the response, not a view name
//        // @RequestParam means it is a parameter from the GET or POST request
//
//        Publication p = new Publication();
//        p.setTitle (title);
//        p.setGenre(genre);
//        publicationRepository.save(p);
//        return "Saved";
//    }

//    @RequestMapping("/")
//    public String index(){
//        return "index";
//    }



//    @RequestMapping("/main")
    @GetMapping("/main") // указываем наш юрл адрес, делаем на главную страничку
    // пока работаем с боллее простой версией
    // вызываем html шаблон main
    public String main(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("name", "Главная страница"); // "title" передаётся в шаблон и в нём, при обращении, вывыдется "Главная старница"
//        Iterable<Publication> publications = publicationRepository.findAll();
//        model.addAttribute("publications",publications);
        return "main";
    }

//    @GetMapping("/") // указываем наш юрл адрес, делаем на главную страничку
//    // пока работаем с боллее простой версией
//    // вызываем html шаблон home
//    public String root(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
//        model.addAttribute("name", "Главная страница"); // "title" передаётся в шаблон и в нём, при обращении, вывыдется "Главная старница"
//        return "index";
//    }
    @GetMapping("/book") // указываем наш юрл адрес, делаем на главную страничку
    // пока работаем с боллее простой версией
    // вызываем html шаблон home
    public String book(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("name", "Главная страница"); // "title" передаётся в шаблон и в нём, при обращении, вывыдется "Главная старница"
        return "book_template";
    }
    @GetMapping("/user") // указываем наш юрл адрес, делаем на главную страничку
    // пока работаем с боллее простой версией
    // вызываем html шаблон home
    public String user(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("name", "Главная страница"); // "title" передаётся в шаблон и в нём, при обращении, вывыдется "Главная старница"
        return "user_template";
    }
}
