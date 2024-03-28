package framexteam.wolframx.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import framexteam.wolframx.authentication.dto.UserDTO;
import framexteam.wolframx.database.entity.User;
import framexteam.wolframx.authentication.exception.UserAlreadyExistsException;
import framexteam.wolframx.database.repository.UserRepository;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public User registerNewUserAccount(UserDTO userDto) throws UserAlreadyExistsException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistsException("There is an account with that email address: "
              + userDto.getEmail());
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());

        return repository.save(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = repository.findByEmail(email);
        UserDTO userDto = new UserDTO();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        return userDto;
    }

    private boolean emailExists(String email) {
        return repository.findByEmail(email) != null;
    }
}