package framexteam.wolframx.authentication.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private static final Logger logger = LogManager.getLogger(RegistrationController.class);

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
    public ResponseEntity<String> registerUserAccount(@RequestBody UserDTO registrationDto) {
        String email = registrationDto.getEmail();
        
        try {
            userService.registerNewUserAccount(registrationDto);
            logger.info("User successfully registered with email: {}", email);
            return ResponseEntity.ok("\"Данные пользователя сохранены\"");
        } catch (UserAlreadyExistsException e) {
            logger.warn("Registration failed: user with email {} already exists", email);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during registration", e);
            return ResponseEntity.badRequest().body("Произошла ошибка при регистрации");
        }
    }

    @GetMapping("/activate/{token}")
    public String activate(@PathVariable String token) {
        boolean isActivated = userService.activateUser(token);

        if (isActivated) {
            return "WolframX: User successfully activated";
        } else {
            return "WolframX: User was not activated";
        }
    }
}
