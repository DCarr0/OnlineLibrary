package ru.mtuci.demo.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import ru.mtuci.demo.service.LoginAttemptsService;

import java.io.IOException;

public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final LoginAttemptsService loginAttemptsService;

    public LoginSuccessHandler(LoginAttemptsService loginAttemptsService) {
        this.loginAttemptsService = loginAttemptsService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);

        String username = authentication.getName();
        loginAttemptsService.loginSucceeded(username);
        loginAttemptsService.resetAttempts(username);
    }
}
