package framexteam.wolframx.authentication.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.*;

import framexteam.wolframx.authentication.dto.UserDTO;
import framexteam.wolframx.authentication.service.UserService;
import framexteam.wolframx.database.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/authorization")
@Tag(name = "Авторизация", description = "API для авторизации пользователей")
public class AuthorizationController {

    private static final Logger logger = LogManager.getLogger(AuthorizationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    private UserService userService;

    @Autowired
    private HttpSession session;

    public AuthorizationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @PostMapping("/signin")
    @Operation(summary = "Авторизация пользователя",
               description = "Аутентифицирует пользователя по email и паролю.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно авторизован",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "401", description = "Неверный email или пароль")
    })
    public ResponseEntity<?> authorizeUser(@RequestBody AuthRequest authRequest) {
        String email = authRequest.getEmail();
        logger.info("Попытка авторизации пользователя: {}", email); // Логирование попытки входа

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, authRequest.getPassword())
            );

            //SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userService.getUserByEmail(email);
            user.setStatus("online");
            userService.saveUser(user);

            session.setAttribute("user", user); // Сохраняем данные пользователя в сессию

            User user2 = (User) session.getAttribute("user");
            System.out.println(user2.getEmail());

            AuthResponse authResponse = new AuthResponse();
            authResponse.setFirstName(user.getFirstName());
            authResponse.setLastName(user.getLastName());
            //authResponse.setEmail(user.getEmail());

            logger.info("Пользователь {} успешно авторизован", email); // Логирование успешной авторизации
            return ResponseEntity.ok(authResponse);
        } catch (BadCredentialsException ex) {
            logger.warn("Неудачная попытка входа для пользователя: {}", email); // Логирование неудачной попытки
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/signout")
    @Operation(summary = "Выход пользователя", description = "Выход пользователя из учетной записи.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно вышел из системы"),
        @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    public ResponseEntity<?> signoutUser() {
        AuthResponse authResponse = new AuthResponse();
        User user = (User) session.getAttribute("user"); // Извлекаем данные пользователя из сессии
        if (user != null) {
            logger.info("Пользователь {} выходит из системы", user.getEmail()); // Логирование выхода
            user.setStatus("offline");
            userService.saveUser(user);
            session.invalidate(); // Очищаем сессию
            return ResponseEntity.ok().build();
        } else {
            logger.warn("Пользователь не авторизован.");
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            return ResponseEntity.ok(authResponse);
        }
    }

    @Getter
    @Setter
    private static class AuthRequest {
        private String email;
        private String password;
    }


    @Getter
    @Setter
    private static class AuthResponse {
        private String firstName;
        private String lastName;
        //private String email;
    }
}