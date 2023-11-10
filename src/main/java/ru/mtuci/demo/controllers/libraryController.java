package ru.mtuci.demo.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mtuci.demo.exceptions.UserAlreadyExistException;
import ru.mtuci.demo.models.Publication;
import ru.mtuci.demo.models.UserData;
import ru.mtuci.demo.repository.PublicationRepository;
import ru.mtuci.demo.repository.UserRepository;
import ru.mtuci.demo.service.UserDetailsServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
    @GetMapping("/main")
    public String main(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        Iterable<Publication> publications = publicationRepository.findAll();
        model.addAttribute("publications",publications);
        return "main";
    }
    @GetMapping("/book_template")
    public String book_template(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        Iterable<Publication> publications = publicationRepository.findAll();
        model.addAttribute("publications",publications);
        return "main";
    }
    @GetMapping("/book")
    public String book(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        return "book_template";
    }
    @GetMapping("/book/details")
    public String bookDetails(@RequestParam(name="id", required=false, defaultValue="User") UUID id, Model model) {
        if (!publicationRepository.existsById(id)){return "redirect:/main";}
        Publication publication = publicationRepository.findById(id).orElseThrow();
//        ArrayList<Publication> array = new ArrayList<>();
//        publication.ifPresent(array::add);
        model.addAttribute("publication", publication);
        //model.addAttribute("publication", PublicationService.findOne(id).orElse(null));
        return "book_details";
    }

    @GetMapping("/book/add")

    public String bookAdd(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        return "book_add";
    }

//    private PublicationService publicationService;
//    @PostMapping("/book/add")
//    //@PreAuthorize("hasAuthority('modification')")
//    public ResponseEntity<PublicationDto> addPublication(@RequestBody PublicationDto publicationDto){
//        PublicationDto savedPublication = publicationService.addPublication(publicationDto);
//
//        return new ResponseEntity<>(savedPublication, HttpStatus.CREATED);
//    }

    @PostMapping("/book/add")
    @PreAuthorize("hasAuthority('modification')")
    public String bookPostAdd(@RequestParam(name="title", required=false) String title,
                              @RequestParam(name="genre", required=false) String genre,
                              @RequestParam(name="link", required=false) String link
                              ){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        Publication publication = new Publication(title,genre,username,LocalDateTime.now(),false,link);
        publicationRepository.save(publication);
        return "redirect:/main";
    }

    @GetMapping("/user")
    public String user(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
//        Optional<User> user = UserRepository.findById(id);
//        model.addAttribute("user",user);
        return "user_template";
    }
}
