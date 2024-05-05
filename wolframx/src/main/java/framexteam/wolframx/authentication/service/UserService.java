package framexteam.wolframx.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import framexteam.wolframx.authentication.dto.UserDTO;
import framexteam.wolframx.database.entity.User;
import framexteam.wolframx.authentication.exception.UserAlreadyExistsException;
import framexteam.wolframx.database.repository.UserRepository;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
@Transactional
public class UserService implements IUserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MailSender mailSender;
    
    @Override
    public User registerNewUserAccount(UserDTO userDto) throws UserAlreadyExistsException {
        if (emailExists(userDto.getEmail())) {
            logger.warn("Attempt to register user with existing email: {}", userDto.getEmail());
            throw new UserAlreadyExistsException("There is an account with that email address: "
              + userDto.getEmail());
        }

        logger.debug("Creating new user: firstName={}, lastName={}, email={}", 
            userDto.getFirstName(), userDto.getLastName(), userDto.getEmail());

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setStatus("offline");
        user.setActivationToken(UUID.randomUUID().toString());
        user.setEnabled(false);

        User savedUser = repository.save(user);
        if (!user.getEmail().isEmpty()) {
            String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to WolframX. Please, click the link to confirm your email: http://25.23.19.72:8080/signup/activate/%s",
                user.getFirstName(),
                user.getActivationToken());

            mailSender.send(user.getEmail(), "Confirm your email", message);
        }
        logger.info("Successfully registered new user with email: {}", savedUser.getEmail());
        return savedUser;
    }

    public User getUserByEmail(String email) {
        logger.debug("Fetching user by email: {}", email);
        User user = repository.findByEmail(email);
        return user;
    }

    public void saveUser(User user) {
        logger.debug("Saving user: {}", user);
        repository.save(user);
    }

    private boolean emailExists(String email) {
        logger.debug("Checking if email exists: {}", email);
        return repository.findByEmail(email) != null;
    }

    public boolean activateUser(String token) {
        User user = repository.findByActivationToken(token);

        if (user == null) {
            return false;
        }

        user.setActivationToken(null);
        user.setEnabled(true);

        repository.save(user);

        return true;
    }
}