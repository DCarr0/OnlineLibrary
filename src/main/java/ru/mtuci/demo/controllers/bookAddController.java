package ru.mtuci.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mtuci.demo.models.Publication;
import ru.mtuci.demo.repository.PublicationRepository;
import ru.mtuci.demo.repository.UserRepository;

import java.time.LocalDateTime;

@Controller
public class bookAddController {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/book/add")
    public String bookAdd(Model model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("create"))) {
            model.addAttribute("message", "Вы не можете добавлять книги, так как не являетесь редактором!");
        }
        return "book_add";}

    @PostMapping("/book/add")
    public String bookPostAdd(@RequestParam(name="title", required=false) String title,
                              @RequestParam(name="genre", required=false) String genre,
                              @RequestParam(name="link", required=false) String link,
                              @RequestParam(name="description", required=false) String description
    ){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var name = userRepository.findUserByEmail(user.getUsername()).getName();
        Publication publication = new Publication(title,genre,name, LocalDateTime.now(),false,link,description,0);

        publicationRepository.save(publication);
        return "redirect:/main";
    }
}
