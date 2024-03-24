package framexteam.wolframx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import framexteam.wolframx.service.UserService;
import jakarta.validation.Valid;
import framexteam.wolframx.dto.UserDTO;
import framexteam.wolframx.exception.UserAlreadyExistsException;

@Controller
@RequestMapping("/registration")
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
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDTO registrationDto) throws UserAlreadyExistsException {
        userService.registerNewUserAccount(registrationDto);
        return "redirect:/registration?success";
    }
}
