package hr.algebra.healthyapp.service;

import hr.algebra.healthyapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUser(String email);

    List<User> getAllPatients();

    List<User> getAllUsers();

    Optional<User> changeAuthority(User user);
}
