package com.ugo.usuaddr.configuration;

import com.ugo.usuaddr.model.Role;
import com.ugo.usuaddr.model.RoleName;
import com.ugo.usuaddr.model.Usuario;
import com.ugo.usuaddr.repository.RoleRepository;
import com.ugo.usuaddr.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
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
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/registrar", "/autenticar", "/atualizar-autenticacao").permitAll();
                    req.anyRequest().authenticated();
                })
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filtroTokenAcesso, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*")); // Or specify exactly: Arrays.asList("Authorization", "Content-Type")
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    CommandLineRunner createFirstAdmin(UsuarioRepository usuarioRepository,
                                       RoleRepository roleRepository,
                                       PasswordEncoder encoder) {
        return args -> {

            usuarioRepository.deleteAll();
            roleRepository.deleteAll();

            Role roleAdmin = roleRepository.save(Role.builder().authority(RoleName.ROLE_ADMIN).build());
            roleRepository.save(Role.builder().authority(RoleName.ROLE_USER).build());

            Usuario admin = Usuario.builder()
                    .email("admin@email.com")
                    .senha(encoder.encode("admin123"))
                    .nome("Administrador das Couves")
                    .perfis(Set.of(roleAdmin))
                    .build();
            usuarioRepository.save(admin);
        };
    }

}