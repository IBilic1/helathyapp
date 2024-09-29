package hr.algebra.healthyapp.service;

import hr.algebra.healthyapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUser(String email);

    Optional<User> changeAuthority(User user);

    List<User> getAllUsers();
}
