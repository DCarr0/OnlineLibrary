package ru.mtuci.demo.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import ru.mtuci.demo.repository.UserRepository;
import ru.mtuci.demo.service.LoginAttemptsService;

import java.io.IOException;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final LoginAttemptsService loginAttemptsService;

    @Autowired
    private UserRepository userRepository;
    public LoginFailureHandler(LoginAttemptsService loginAttemptsService) {
        this.loginAttemptsService = loginAttemptsService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        super.onAuthenticationFailure(request, response, exception);

        String username = request.getParameter("username");
        var user = userRepository.findUserByEmail(username);
        if (username != null) {
            loginAttemptsService.loginFailed(username);
            if (loginAttemptsService.isBlocked(username)) {
                user.setBan(true);
                userRepository.save(user);
            }
        }
    }
}
