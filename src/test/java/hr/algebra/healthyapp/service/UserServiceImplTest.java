package hr.algebra.healthyapp.service;

import hr.algebra.healthyapp.model.User;
import hr.algebra.healthyapp.repository.UserRepository;
import hr.algebra.healthyapp.service.impl.UserServiceImpl;
import hr.algebra.healthyapp.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUser_UserExists() {
        // Arrange
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // Act
        Optional<User> result = userService.getUser(email);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo(email);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testGetUser_UserDoesNotExist() {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.getUser(email);

        // Assert
        assertThat(result).isEmpty();
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testChangeAuthority_UserExists() {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Role.ADMIN);

        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setRole(Role.USER);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        Optional<User> updatedUser = userService.changeAuthority(user);

        // Assert
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getRole()).isEqualTo(Role.ADMIN);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testChangeAuthority_UserDoesNotExist() {
        // Arrange
        User user = new User();
        user.setEmail("notfound@example.com");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        // Act
        Optional<User> updatedUser = userService.changeAuthority(user);

        // Assert
        assertThat(updatedUser).isEmpty();
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testGetAllPatients() {
        // Arrange
        User patient1 = new User();
        patient1.setRole(Role.USER);
        User patient2 = new User();
        patient2.setRole(Role.USER);
        User admin = new User();
        admin.setRole(Role.ADMIN);

        when(userRepository.findAll()).thenReturn(List.of(patient1, patient2, admin));

        // Act
        List<User> patients = userService.getAllPatients();

        // Assert
        assertThat(patients).hasSize(2);
        assertTrue(patients.stream().allMatch(user -> user.getRole() == Role.USER));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        User user1 = new User();
        user1.setEmail("user1@example.com");
        User user2 = new User();
        user2.setEmail("user2@example.com");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act
        List<User> users = userService.getAllUsers();

        // Assert
        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getEmail)
                .containsExactlyInAnyOrder("user1@example.com", "user2@example.com");
        verify(userRepository, times(1)).findAll();
    }
}
