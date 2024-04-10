package framexteam.wolframx.controller.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import framexteam.wolframx.authentication.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import framexteam.wolframx.authentication.dto.UserDTO;
import framexteam.wolframx.authentication.exception.UserAlreadyExistsException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/signup")
@Tag(name = "Регистрация", description = "API для регистрации пользователей")
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Регистрация нового пользователя", 
               description = "Регистрирует нового пользователя в системе.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации данных пользователя"),
        @ApiResponse(responseCode = "409", description = "Пользователь с таким email уже существует")
    })
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
