package ru.mtuci.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mtuci.demo.models.Comment;
import ru.mtuci.demo.models.Publication;
import ru.mtuci.demo.models.UserRate;
import ru.mtuci.demo.repository.CommentRepository;
import ru.mtuci.demo.repository.PublicationRepository;
import ru.mtuci.demo.repository.UserRateRepository;
import ru.mtuci.demo.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class bookDetailsController {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRateRepository userRateRepository;

    @GetMapping("/book/details")
    public String bookDetails(@RequestParam(name="id", required=false, defaultValue="") UUID id,
                              @RequestParam(name="action", required=false, defaultValue="") String action,
                              Model model) {
        if (!publicationRepository.existsById(id)){return "redirect:/main";}

        Publication publication = publicationRepository.findById(id).orElseThrow();
        if (publication.getBan()) return "redirect:/main";

        model.addAttribute("publication", publication);

        List<Comment> comments = commentRepository.findByPublicationId(id);
        model.addAttribute("comments", comments);

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean hasModificationRole = user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("modification"));
        boolean hasDeleteRole = user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("delete"));

        model.addAttribute("hasModificationRole", hasModificationRole);
        model.addAttribute("hasDeleteRole", hasDeleteRole);

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
                                 @RequestParam(name="commentary", required=false) String commentary,
                                 @RequestParam(name = "rate", required = false) Integer rate,
                                 RedirectAttributes redirectAttributes)
    {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Publication publication = publicationRepository.findById(id).orElseThrow();
        if ("redact".equals(action)) {
            if (!user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("modification"))){
                redirectAttributes.addFlashAttribute("message", "Вы не можете редактировать публикации, так как не являетесь Модератором");
                return "redirect:/book/details?id=" + id.toString();
            };
            publication.setTitle(title);
            publication.setGenre(genre);
            publication.setLink(link);
            publication.setDescription(description);
            publication.setDate(LocalDateTime.now());
            var name = userRepository.findUserByEmail(user.getUsername()).getName();
            publication.setPublisherName(name);
            publication.setBan(false);

            publicationRepository.save(publication);
        }

        if ("remove".equals(action)) {
            if (!user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("delete"))){
                redirectAttributes.addFlashAttribute("message", "Вы не можете удалять публикации, так как не являетесь Админом");
                return "redirect:/book/details?id=" + id.toString();
            };
            publication.setBan(true);

            publicationRepository.save(publication);

            return "redirect:/main";
        }

        if ("addCom".equals(action)){
            var name = userRepository.findUserByEmail(user.getUsername()).getName();
            Comment comment = new Comment(commentary,id,name,LocalDateTime.now(),false);
            commentRepository.save(comment);
        }
        if ("addRate".equals(action)){
            var userId = userRepository.findUserByEmail(user.getUsername()).getId();

            Iterable<UserRate> userRates = userRateRepository.findByUserId(userId);
            for (UserRate userrate:userRates) {
                if (userrate.getPublicationId().equals(id)){
                    redirectAttributes.addFlashAttribute("message", "Вы уже ставили оценку!");
                    return "redirect:/book/details?id=" + id.toString();
                }
            }

            UserRate userRate = new UserRate(userId,id);
            userRateRepository.save(userRate);

            publication.setRate(publication.getRate() + rate);
            publicationRepository.save(publication);
        }

        if ("removeCom".equals(action)) {
            if (!user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("modification"))){
                redirectAttributes.addFlashAttribute("message", "Вы не можете удалять комментарии, так как не являетесь Модератором");
                return "redirect:/book/details?id=" + id.toString();
            };

            Comment comment = commentRepository.findById(actionCom).orElseThrow();
            comment.setBan(true);

            commentRepository.save(comment);
        }

        return "redirect:/book/details?id=" + id.toString();
    }
}
