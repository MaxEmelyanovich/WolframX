package framexteam.wolframx.database.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true, length = 45)
    private String email;
     
    @Column(nullable = false, length = 64)  
    private String password;
     
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
     
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;
    
    @Column(nullable = false, length = 10)
    private String status;

    @Column(name = "activation_token", length = 36)
    private String activationToken;

    @Column(nullable = false)
    private boolean enabled;
}