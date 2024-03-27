package framexteam.wolframx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import framexteam.wolframx.authentication.service.UserService;
import framexteam.wolframx.database.entity.User;
import jakarta.validation.Valid;
import framexteam.wolframx.authentication.dto.UserDTO;
import framexteam.wolframx.authentication.exception.UserAlreadyExistsException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/signup")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserDTO userRegistrationDto() {
        return new UserDTO();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public User registerUserAccount(@RequestBody @ModelAttribute("user") @Valid UserDTO registrationDto) throws UserAlreadyExistsException {
        return userService.registerNewUserAccount(registrationDto);
    }
}
