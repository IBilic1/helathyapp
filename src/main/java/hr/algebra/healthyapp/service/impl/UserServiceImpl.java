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
        if (user.getRole() == Role.SYSTEM_USER) {
            throw new IllegalArgumentException("Authority cannot be changed to a system user");
        }

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
    public List<User> getAllPatients() {
        return userRepository.findAll().stream()
                .filter((patient) -> patient.getRole() == Role.PATIENT)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .filter((patient) -> patient.getRole() != Role.SYSTEM_USER)
                .collect(Collectors.toList());
    }
}
