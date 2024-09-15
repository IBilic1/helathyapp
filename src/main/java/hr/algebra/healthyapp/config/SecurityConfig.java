package hr.algebra.healthyapp.config;

import hr.algebra.healthyapp.auth.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableScheduling
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService userDetailsService;

    @Value("${default-success}")
    private String defaultSuccessUrl;

    @Value("${default-error}")
    private String defaultErrorUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry.anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("http://localhost:3000/home", true).failureUrl("http://localhost:3000/error")
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(userDetailsService)));

        return http.build();
    }
}
