package hr.algebra.healthyapp.service;

import hr.algebra.healthyapp.model.User;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUser(String email);

    List<User> getAllPatient();
}
