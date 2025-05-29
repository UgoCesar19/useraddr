package com.ugo.usuaddr.configuration;

import com.ugo.usuaddr.model.Role;
import com.ugo.usuaddr.model.Usuario;
import com.ugo.usuaddr.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:security.properties")
public class SecurityConfig {

    @Autowired
    private FiltroTokenAcesso filtroTokenAcesso;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encriptador(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/public/**", "/autenticar", "/atualizar-autenticacao", "/h2-console/**").permitAll();
                    req.anyRequest().authenticated();
                }).addFilterBefore(filtroTokenAcesso, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            Role adminRole = new Role("ROLE_ADMIN");

            Usuario admin = new Usuario(null, "admin@email.com", encoder.encode("admin123"), "Administrador das Couves", Set.of(adminRole));
            userRepository.save(admin);
        };
    }

}