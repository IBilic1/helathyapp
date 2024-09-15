package hr.algebra.healthyapp.service;

import hr.algebra.healthyapp.model.User;
import hr.algebra.healthyapp.repository.UserRepository;
import hr.algebra.healthyapp.service.impl.UserServiceImpl;
import hr.algebra.healthyapp.user.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        Optional<User> result = userServiceImpl.getUser("email");
        Assertions.assertNull(result);
    }

    @Test
    void testGetAllPatient() {
        User patient = new User(0, "firstName lastName", "email", Role.USER);

        when(userRepository.findAll()).thenReturn(Collections.singletonList(patient));

        List<User> result = userServiceImpl.getAllPatient();
        Assertions.assertEquals(Collections.singletonList(patient), result);
    }
}
