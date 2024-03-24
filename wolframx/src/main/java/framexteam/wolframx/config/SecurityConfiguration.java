package framexteam.wolframx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import framexteam.wolframx.service.MyUserDetailsService;
 
@Configuration
public class SecurityConfiguration   {
     
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
         
        return authProvider;
    }
 
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
         
        http.authenticationProvider(authenticationProvider());
         
        // http.authorizeHttpRequests().antMatchers(
        //         "/registration**",
        //         "/js/**",
        //         "/css/**",
        //         "/img/**").permitAll()
        //     .anyRequest().authenticated()
        //     .and()
        //     .formLogin()
        //     .loginPage("/login")
        //     .permitAll()
        //     .and()
        //     .logout()
        //     .invalidateHttpSession(true)
        //     .clearAuthentication(true)
        //     .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        //     .logoutSuccessUrl("/login?logout")
        //     .permitAll();
        http.authorizeHttpRequests(auth ->
            auth.requestMatchers("/users",
            "/js/**",
            "/css/**",
            "/img/**").authenticated()
            .anyRequest().permitAll()
            )
            .formLogin(login ->
                login.usernameParameter("email")
                .defaultSuccessUrl("/users")
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
