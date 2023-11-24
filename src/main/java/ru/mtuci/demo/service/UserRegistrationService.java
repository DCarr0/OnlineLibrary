package ru.mtuci.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mtuci.demo.exceptions.InvalidTokenException;
import ru.mtuci.demo.exceptions.UnkownIdentifierException;
import ru.mtuci.demo.exceptions.UserAlreadyExistException;
import ru.mtuci.demo.models.User;
import ru.mtuci.demo.models.UserData;
import ru.mtuci.demo.models.UserRole;
import ru.mtuci.demo.repository.RoleRepository;
import ru.mtuci.demo.repository.UserRepository;

@Service("userRegistrationService")
@RequiredArgsConstructor
public class UserRegistrationService implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void registration(UserData user) throws UserAlreadyExistException {

        if(checkIfUserExist(user.getEmail())){
            throw new UserAlreadyExistException("User already exists for this email");
        }
        User User = new User();
        BeanUtils.copyProperties(user, User);

        User.setRole(UserRole.USER);

        encodePassword(User, user);
        userRepository.save(User);
    }


    @Override
    public boolean checkIfUserExist(String email) {
        var user = userRepository.findByEmail(email).isPresent();
        return user;
    }

    @Override
    public void sendRegistrationConfirmationEmail(User user) {

    }

    @Override
    public boolean verifyUser(String token) throws InvalidTokenException {
        return false;
    }

    @Override
    public User getUserById(String id) throws UnkownIdentifierException {
        return null;
    }

    private void encodePassword( User applicationUser, UserData user){
        applicationUser.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
