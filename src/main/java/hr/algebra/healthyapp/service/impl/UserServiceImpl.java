package hr.algebra.healthyapp.service.impl;

import hr.algebra.healthyapp.model.User;
import hr.algebra.healthyapp.repository.UserRepository;
import hr.algebra.healthyapp.service.UserService;
import hr.algebra.healthyapp.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> changeAuthority(User user) {
        Optional<User> oUserToUpdate = userRepository.findByEmail(user.getEmail());
        if (oUserToUpdate.isEmpty()) {
            return Optional.empty();
        }

        User userToUpdate = oUserToUpdate.get();
        userToUpdate.setRole(user.getRole());
        userRepository.save(userToUpdate);
        return Optional.of(userToUpdate);
    }

    @Override
    public List<User> getAllPatient() {
        return userRepository.findAll().stream()
                .filter((patient) -> patient.getRole() == Role.USER)
                .collect(Collectors.toList());
    }
}
