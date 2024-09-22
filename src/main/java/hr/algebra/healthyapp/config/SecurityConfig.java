package hr.algebra.healthyapp.config;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import hr.algebra.healthyapp.auth.OAuth2UserService;
import hr.algebra.healthyapp.repository.CustomCsrfTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableScheduling
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService userDetailsService;

    @Value("${security.urls.default-success}")
    private String defaultSuccessUrl;

    @Value("${security.urls.default-error}")
    private String defaultErrorUrl;

    @Value("${security.urls.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/logout") // Disable CSRF protection for logout
                        .csrfTokenRepository(new CustomCsrfTokenRepository())
                )
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl(defaultSuccessUrl, true).failureUrl(defaultErrorUrl)
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(userDetailsService)));
        return http.build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
