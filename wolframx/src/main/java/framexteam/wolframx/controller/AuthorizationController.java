package framexteam.wolframx.controller;

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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/signin")
public class AuthorizationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private UserService userService;

    public AuthorizationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @PostMapping
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
}
