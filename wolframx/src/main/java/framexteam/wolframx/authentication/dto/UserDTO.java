package framexteam.wolframx.authentication.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
