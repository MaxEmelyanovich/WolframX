package framexteam.wolframx.authentication.service;

import framexteam.wolframx.database.entity.User;
import framexteam.wolframx.authentication.exception.UserAlreadyExistsException;
import framexteam.wolframx.authentication.dto.UserDTO;

public interface IUserService {
    User registerNewUserAccount(UserDTO userDto) throws UserAlreadyExistsException;
}
