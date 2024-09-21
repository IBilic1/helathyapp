package hr.algebra.healthyapp.auth;

import hr.algebra.healthyapp.model.User;
import hr.algebra.healthyapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @SneakyThrows
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        log.trace("Load user {}", oAuth2UserRequest);
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        Optional<Object> oEmail = Optional.ofNullable(oAuth2User.getAttributes().get("email"));
        if (oEmail.isEmpty()) {
            throw new OAuth2AuthenticationException("[ERROR] Email is required");
        }

        User userInfoDto = User
                .builder()
                .name(String.valueOf(oAuth2User.getAttributes().get("name")))
                .email(String.valueOf(oEmail.get()))
                .build();

        Optional<User> userOptional = userRepository.findByEmail(userInfoDto.getEmail());
        log.trace("User is {}", userOptional);
        User user = userOptional
                .map(existingUser -> updateExistingUser(existingUser, userInfoDto))
                .orElseGet(() -> registerNewUser(userInfoDto));
        return new CustomOAuth2User(oAuth2User);
    }

    private User registerNewUser(User userInfoDto) {
        return userRepository.save(userInfoDto);
    }

    private User updateExistingUser(User existingUser, User userInfoDto) {
        existingUser.setName(userInfoDto.getName());
        return userRepository.save(existingUser);
    }
}
