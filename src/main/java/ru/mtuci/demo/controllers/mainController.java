package ru.mtuci.demo.controllers;

import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mtuci.demo.exceptions.UserAlreadyExistException;
import ru.mtuci.demo.models.Publication;
import ru.mtuci.demo.models.UserData;
import ru.mtuci.demo.repository.PublicationRepository;
import ru.mtuci.demo.service.UserRegistrationService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ru.mtuci.demo.service.PublicationService.distinctByKey;


@Controller
public class mainController {

    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private PublicationRepository publicationRepository;


    @GetMapping("/")
    public String welcome() {
        return "../static/index.html";
    }

    @GetMapping("/main")
    public String main(Model model) {
        Iterable<Publication> publications = publicationRepository.findAll();
        model.addAttribute("publications",publications);
        return "main";
    }

    @PostMapping("/main")
    public String searchPage(@RequestParam("searchString") String searchString, Model model) {

        if (searchString != null) {
            try {
                List<Publication> searchResults = new ArrayList<>();

                Iterable<Publication> search1 = publicationRepository.findByTitleContainingIgnoreCase(searchString);
                search1.forEach(searchResults::add);

                Iterable<Publication> search2 = publicationRepository.findByGenreContainingIgnoreCase(searchString);
                search2.forEach(searchResults::add);

                List<Publication> Results = searchResults.stream()
                        .filter(distinctByKey(Publication::getId))
                        .collect(Collectors.toList());
                model.addAttribute("publications", Results);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "main";
    }

    @GetMapping("/autorisation")
    public String autorisation(Model model){

        return "autorisation";
    }
    @GetMapping("/registration")
    public String registration(@RequestParam(name="title", required=false, defaultValue="User") String name, Model model) {
        model.addAttribute("userData", new UserData());
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegistration(final @Valid UserData userData, final BindingResult bindingResult, final Model model,@RequestParam("g-recaptcha-response") String captchaResponse) throws IOException, InterruptedException {
        String captchaSecret = "6LclkTcpAAAAAOOIa0Xlpz8sbvrmJFLUzhdUK6d7";
        final String captchaURL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
        String url = String.format(captchaURL,captchaSecret,captchaResponse);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString("id=10"))
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonObject = new JSONObject(response.body().toString());
        if (jsonObject.getBoolean("success")){
            String password = userData.getPassword();
            Pattern uppercasePattern = Pattern.compile("[A-Z]");
            Matcher uppercaseMatcher = uppercasePattern.matcher(password);
            Pattern digitPattern = Pattern.compile("\\d");
            Matcher digitMatcher = digitPattern.matcher(password);
            if (password.length() < 8 || !uppercaseMatcher.find() || !digitMatcher.find()) {
                model.addAttribute("registrationError", "Пароль должен быть не менее 8 символов и содержать хотя бы одну заглавную букву и цифру!");
                return "registration";
            }

            try {
                userRegistrationService.registration(userData);
            }catch (UserAlreadyExistException e){
                model.addAttribute("registrationError", "Учетная запись для этого адреса электронной почты уже существует.");
                return "registration";
            }
            return "redirect:/main";
        }
        model.addAttribute("registrationError", "Пройдите капчу!");
        return "registration";
    }
}