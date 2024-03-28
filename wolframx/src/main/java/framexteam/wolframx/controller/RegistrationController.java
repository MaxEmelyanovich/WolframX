package framexteam.wolframx.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import framexteam.wolframx.authentication.service.UserService;
import framexteam.wolframx.authentication.dto.UserDTO;
import framexteam.wolframx.authentication.exception.UserAlreadyExistsException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/signup")
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> registerUserAccount(@RequestBody UserDTO registrationDto) throws UserAlreadyExistsException {
        String firstname = registrationDto.getFirstName();
        String lastname = registrationDto.getLastName();
        String email = registrationDto.getEmail();
        String password = registrationDto.getPassword();
        System.out.println("Firstname: " + firstname);
        System.out.println("Lastname: " + lastname);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        userService.registerNewUserAccount(registrationDto);
        return ResponseEntity.ok("\"Данные пользователя сохранены\"");
    }
}
