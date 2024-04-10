package framexteam.wolframx.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import framexteam.wolframx.authentication.dto.UserDTO;
import framexteam.wolframx.authentication.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/signin")
@Tag(name = "Авторизация", description = "API для авторизации пользователей")
public class AuthorizationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private UserService userService;

    public AuthorizationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Авторизация пользователя",
               description = "Аутентифицирует пользователя по email и паролю.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь успешно авторизован",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "401", description = "Неверный email или пароль")
    })
    public ResponseEntity<?> authenticateUser(@RequestBody UserDTO authorizationDto) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authorizationDto.getEmail(),
                authorizationDto.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDTO userDTO = userService.getUserByEmail(authorizationDto.getEmail());

        return ResponseEntity.ok(userDTO);
    }

    private static class AuthRequest {

        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        
        private static class AuthResponse {
            private String firstName;
            private String lastName;

            public String getFirstName() {
                return firstName;
            }
    
            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }
    
            public String getLastName() {
                return lastName;
            }
    
            public void setLastName(String lastName) {
                this.lastName = lastName;
            }
        }
    }
}
