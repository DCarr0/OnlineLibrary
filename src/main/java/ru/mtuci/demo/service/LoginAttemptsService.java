package ru.mtuci.demo.service;

public interface LoginAttemptsService {
    void loginSucceeded(String username);
    void loginFailed(String username);
    boolean isBlocked(String username);
    void resetAttempts(String username);
}
