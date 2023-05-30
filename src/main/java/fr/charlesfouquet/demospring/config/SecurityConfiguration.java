package fr.charlesfouquet.demospring.config;

import fr.charlesfouquet.demospring.services.UserLoginDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserLoginDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
            .requestMatchers("/login", "/register").permitAll()
            .requestMatchers("/addEmployee").hasAuthority("ADMIN")
            .requestMatchers("/addEmployee/*").hasAuthority("ADMIN")
            .requestMatchers("/articles").hasAnyAuthority("USER", "MEMBRE", "ADMIN")
            .requestMatchers("/articles/*").hasAnyAuthority("MEMBRE", "ADMIN")
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .loginPage("/login")
            .usernameParameter("email")
            .defaultSuccessUrl("/")
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/")
            .permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/accessDenied");
        return http.build();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}