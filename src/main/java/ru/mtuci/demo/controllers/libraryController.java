package ru.mtuci.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mtuci.demo.models.Comment;
import ru.mtuci.demo.models.Publication;
import ru.mtuci.demo.repository.CommentRepository;
import ru.mtuci.demo.repository.PublicationRepository;
import ru.mtuci.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class libraryController {

    @Autowired
    private PublicationRepository publicationRepository;

    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/main")
    public String main(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        Iterable<Publication> publications = publicationRepository.findAll();
        model.addAttribute("publications",publications);
        return "main";
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
    }

    private boolean isModerator() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("MODERATOR"));
    }

    @GetMapping("/book/details")
    public String bookDetails(@RequestParam(name="id", required=false, defaultValue="") UUID id,
                              @RequestParam(name="action", required=false, defaultValue="") String action,
                              Model model) {
//        String Sid = id.toString().replaceAll("-", "");
        if (!publicationRepository.existsById(id)){return "redirect:/main";}
        Publication publication = publicationRepository.findById(id).orElseThrow();
        model.addAttribute("publication", publication);

        List<Comment> comments = commentRepository.findByPublicationId(id);
        model.addAttribute("comments", comments);

        if ("redact".equals(action)) {
            return "book_details_redact";
        }

        return "book_details";
    }

    @PostMapping("/book/details")
    public String bookPostRedact(@RequestParam(name="id", required=false) UUID id,
                                 @RequestParam(name="title", required=false) String title,
                                 @RequestParam(name="genre", required=false) String genre,
                                 @RequestParam(name="link", required=false) String link,
                                 @RequestParam(name="description", required=false) String description,
                                 @RequestParam(name="action", required=false, defaultValue="") String action,
                                 @RequestParam(name="actionCom", required=false, defaultValue="") UUID actionCom,
                                 @RequestParam(name="commentary", required=false) String commentary)
    {

        if ("redact".equals(action)) {
            Publication publication = publicationRepository.findById(id).orElseThrow();
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            publication.setTitle(title);
            publication.setGenre(genre);
            publication.setLink(link);
            publication.setDescription(description);
            publication.setDate(LocalDateTime.now());
            publication.setPublisherName(user.getUsername());
            publication.setBan(false);

            publicationRepository.save(publication);
        }

        if ("remove".equals(action)) {
            Publication publication = publicationRepository.findById(id).orElseThrow();
            publication.setBan(true);

            publicationRepository.save(publication);

            return "redirect:/main";
        }

        if ("addCom".equals(action)){
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Comment comment = new Comment(commentary,id,user.getUsername(),LocalDateTime.now(),false);
            commentRepository.save(comment);
        }

        if ("removeCom".equals(action)) {
            Comment comment = commentRepository.findById(actionCom).orElseThrow();
            comment.setBan(true);

            commentRepository.save(comment);
        }

        return "redirect:/book/details?id=" + id.toString();
    }

    @GetMapping("/book/add")
    public String bookAdd(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        return "book_add";
    }

    @PostMapping("/book/add")
//    @PreAuthorize("hasAuthority('modification')")
    public String bookPostAdd(@RequestParam(name="title", required=false) String title,
                              @RequestParam(name="genre", required=false) String genre,
                              @RequestParam(name="link", required=false) String link,
                              @RequestParam(name="description", required=false) String description
                              ){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Publication publication = new Publication(title,genre,user.getUsername(),LocalDateTime.now(),false,link,description);
        publicationRepository.save(publication);
        return "redirect:/main";
    }
    @Autowired
    public libraryController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @GetMapping("/user")
    public String user(Model model) {
        UserDetails  user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userRepository.findByName(user.getUsername()));

        return "user_template";
    }
    @GetMapping("/")
    public String welcome() {
        return "../static/index.html";
    }
}
