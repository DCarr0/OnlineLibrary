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
import ru.mtuci.demo.models.Publication;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.models.UserRole;
import ru.mtuci.demo.repository.PublicationRepository;
import ru.mtuci.demo.repository.UserRepository;
import ru.mtuci.demo.service.PublicationService;

import java.util.List;
import java.util.UUID;

@Controller
public class userController {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PublicationService publicationService;

    @GetMapping("/user")
    public String userGet(Model model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isModerator = userRepository.findUserByEmail(user.getUsername()).getRole().toString().equals("MODERATOR");
        boolean isAdmin = userRepository.findUserByEmail(user.getUsername()).getRole().toString().equals("ADMIN");

        model.addAttribute("user", userRepository.findUserByEmail(user.getUsername()));
        model.addAttribute("isModerator", isModerator);
        model.addAttribute("isAdmin", isAdmin);

        var id = userRepository.findUserByEmail(user.getUsername()).getId();

        List<Publication> publications = publicationService.findRatedPublicationsByUserId(id);

        model.addAttribute("publications", publications);

        return "user_template";
    }

    @PostMapping("/user")
    public String userPost(@RequestParam(name="userId", required=false) UUID userId,
                           @RequestParam(name="requestToRedactor", required=false) Boolean requestToRedactor,
                           RedirectAttributes redirectAttributes) {
        if(requestToRedactor != null){
            User user = userRepository.findById(userId).orElseThrow();
            if (user.getRequestToRedactor().equals(true) && requestToRedactor){
                redirectAttributes.addFlashAttribute("errorMessege","Вы уже ранее подавали заявку на редактора!");
                return "redirect:/user";
            }
            user.setRequestToRedactor(requestToRedactor);
            userRepository.save(user);
        }
        if (requestToRedactor)
            redirectAttributes.addFlashAttribute("messege","Заявка отправлена на рассмотрение, модератор рассмотрит её ближайшее время!");
        else
            redirectAttributes.addFlashAttribute("messege","Вы решили отказаться быть редактором, в ближайшее время модератор одобрит вашу заявку!");
        return "redirect:/user";
    }


    @GetMapping("/moderatorPanel")
    public String moderatorPanelGet(Model model){
        UserDetails  user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("modification")))) return "redirect:/main";

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users",users);

        return "moderator_panel";
    }

    @PostMapping("/moderatorPanel")
    public String moderatorPanelPost(@RequestParam(name="userId", required=false) UUID userId,
                                     @RequestParam(name="newRole", required=false) String newRole,
                                     RedirectAttributes redirectAttributes){
        if(newRole != null) {
            User user = userRepository.findById(userId).orElseThrow();
            if (user.getRole().toString().equals(newRole)){
                redirectAttributes.addFlashAttribute("errorMessege","Пользователь уже имеет эту роль!!!");
                return "redirect:/moderatorPanel";
            }
            if (user.getRole().toString().equals("ADMIN") || user.getRole().toString().equals("MODERATOR")){
                redirectAttributes.addFlashAttribute("errorMessege","Вы не имеете таких прав!!!");
                return "redirect:/moderatorPanel";
            }
            user.setRole(UserRole.valueOf(newRole));
            userRepository.save(user);
        }
        else if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow();
            if (user.getRole().toString().equals("ADMIN") || user.getRole().toString().equals("MODERATOR")){
                redirectAttributes.addFlashAttribute("errorMessege","Вы не имеете таких прав!!!");
                return "redirect:/moderatorPanel";
            }
            user.setBan(!user.getBan());
            userRepository.save(user);
        }

        return "redirect:/moderatorPanel";
    }

    @GetMapping("/adminPanel")
    public String adminPanelGet(Model model){
        UserDetails  user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("delete")))) return "redirect:/main";

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users",users);

        Iterable<Publication> publications = publicationRepository.findAll();
        model.addAttribute("publications", publications);

        return "admin_panel";
    }

    @PostMapping("/adminPanel")
    public String adminPanelPost(@RequestParam(name="userId", required=false) UUID userId,
                                 @RequestParam(name="publicationId", required=false) UUID publicationId,
                                 @RequestParam(name="newRole", required=false) String newRole,
                                 RedirectAttributes redirectAttributes){
        if(newRole != null) {
            User user = userRepository.findById(userId).orElseThrow();
            if (user.getRole().toString().equals(newRole)){
                redirectAttributes.addFlashAttribute("errorMessege","Пользователь уже имеет эту роль!!!");
                return "redirect:/adminPanel";
            }
            if (user.getRole().toString().equals("ADMIN")){
                redirectAttributes.addFlashAttribute("errorMessege","Вы не имеете таких прав!!!");
                return "redirect:/adminPanel";
            }
            user.setRole(UserRole.valueOf(newRole));
            userRepository.save(user);
        }
        else if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow();
            if (user.getRole().toString().equals("ADMIN")){
                redirectAttributes.addFlashAttribute("errorMessege","Вы не имеете таких прав!!!");
                return "redirect:/adminPanel";
            }
            user.setBan(!user.getBan());
            userRepository.save(user);
        } else if (publicationId != null) {
            Publication publication = publicationRepository.findById(publicationId).orElseThrow();
            publication.setBan(!publication.getBan());
            publicationRepository.save(publication);
        }

        return "redirect:/adminPanel";
    }
}
