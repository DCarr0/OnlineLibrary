package ru.mtuci.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mtuci.demo.details.PublicationDto;
import ru.mtuci.demo.models.Publication;
import ru.mtuci.demo.repository.PublicationRepository;
import ru.mtuci.demo.service.PublicationService;

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

    @GetMapping("/book/add")
    public String bookAdd(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        return "book_add";
    }

    private PublicationService publicationService;
    @PostMapping("/book/add")
    public ResponseEntity<PublicationDto> addPublication(@RequestBody PublicationDto publicationDto){
        PublicationDto savedPublication = publicationService.addPublication(publicationDto);

        return new ResponseEntity<>(savedPublication, HttpStatus.CREATED);
    }


//    @PostMapping("/book/add")
//    public String bookPostAdd(@RequestParam(name="title", required=false, defaultValue="User") String title,
//                              @RequestParam(name="genre", required=false, defaultValue="User") String genre,
//                              @RequestParam(name="link", required=false, defaultValue="User") String link,
//                              Model model){
//        Publication publication = new Publication(title,genre,link);
//        PublicationRepository.save(publication);
//        return "redirect:/blog";
//    }

    @GetMapping("/user")
    public String user(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("name", "Главная страница");
        return "user_template";
    }
}
