package framexteam.wolframx.authentication.config;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import framexteam.wolframx.authentication.service.MyUserDetailsService;
 
@Configuration
public class SecurityConfiguration   {

    private static final Logger logger = LogManager.getLogger(SecurityConfiguration.class); 
     
    @Autowired
    private MyUserDetailsService userDetailsService;
 
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        logger.debug("Created DaoAuthenticationProvider bean");
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider authenticationProvider) {
        return new ProviderManager(Arrays.asList(authenticationProvider));
    }
 
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authenticationProvider(authenticationProvider());
         
        
        http.authorizeHttpRequests(auth -> {
            // auth.requestMatchers("/users",
            // "/js/**",
            // "/css/**",
            // "/img/**").authenticated()
            // .anyRequest().permitAll();
            logger.debug("Configuring HttpSecurity with authorized paths: {}", auth.requestMatchers("/users",
            "/js/**",
            "/css/**",
            "/img/**").authenticated()
            .anyRequest().permitAll());
        })
            .formLogin(login ->
                login.usernameParameter("email")
                //.defaultSuccessUrl("/users")
                .permitAll()
            )
            .logout(logout -> logout.logoutSuccessUrl("/login?logout")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .permitAll()
            
        );
         
        return http.build();
    }  
}
