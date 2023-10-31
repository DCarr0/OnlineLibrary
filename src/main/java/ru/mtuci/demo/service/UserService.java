package ru.mtuci.demo.service;

import ru.mtuci.demo.exceptions.InvalidTokenException;
import ru.mtuci.demo.exceptions.UnkownIdentifierException;
import ru.mtuci.demo.exceptions.UserAlreadyExistException;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.models.UserData;

public interface UserService {
    void registration(final UserData user) throws UserAlreadyExistException;
    boolean checkIfUserExist(final String email);
    void sendRegistrationConfirmationEmail(final User user);
    boolean verifyUser(final String token) throws InvalidTokenException;
    User getUserById(final String id) throws UnkownIdentifierException;
}
