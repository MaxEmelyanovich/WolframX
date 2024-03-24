package framexteam.wolframx.service;

// import org.springframework.security.core.userdetails.UserDetailsService;

import framexteam.wolframx.entity.User;
import framexteam.wolframx.exception.UserAlreadyExistsException;
import framexteam.wolframx.dto.UserDTO;

public interface IUserService {
    User registerNewUserAccount(UserDTO userDto) throws UserAlreadyExistsException;
}
