package framexteam.wolframx.authentication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import framexteam.wolframx.database.entity.User;
import framexteam.wolframx.database.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(MyUserDetailsService.class);
 
    @Autowired
    private UserRepository userRepository;
    
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("Searching for user with email: {}", email); // Логирование поиска пользователя
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.warn("User not found with email: {}", email); // Логирование отсутствия пользователя
            throw new UsernameNotFoundException("No user found with username: " + email);
        } else {
            logger.debug("Found user: email={}, status={}", // Логирование найденного пользователя
                    user.getEmail(), user.getStatus());
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        
        return new org.springframework.security.core.userdetails.User(
          user.getEmail(), user.getPassword(), enabled, accountNonExpired,
          credentialsNonExpired, accountNonLocked, getAuthorities());
    }
    
    private static List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // for (String role : roles) {
        //     authorities.add(new SimpleGrantedAuthority(role));
        // }
        return authorities;
    }
}
