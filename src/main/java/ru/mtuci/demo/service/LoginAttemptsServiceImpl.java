package ru.mtuci.demo.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginAttemptsServiceImpl implements LoginAttemptsService {

    private final int MAX_ATTEMPTS = 3; // Максимальное количество попыток

    private final Map<String, Integer> attempts = new HashMap<>();

    @Override
    public void loginSucceeded(String username) {
        attempts.remove(username);
    }

    @Override
    public void loginFailed(String username) {
        int attemptsCount = attempts.getOrDefault(username, 0);
        attempts.put(username, attemptsCount + 1);
    }

    @Override
    public boolean isBlocked(String username) {
        return attempts.getOrDefault(username, 0) >= MAX_ATTEMPTS;
    }

    @Override
    public void resetAttempts(String username) { attempts.put(username, 0);}
}
