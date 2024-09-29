package hr.algebra.healthyapp.service;

import hr.algebra.healthyapp.exception.AppointmentExistsException;
import hr.algebra.healthyapp.model.Appointment;
import hr.algebra.healthyapp.model.User;
import hr.algebra.healthyapp.repository.AppointmentRepository;
import hr.algebra.healthyapp.repository.UserRepository;
import hr.algebra.healthyapp.user.Role;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("unit")
class AppointmentServiceTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentService appointmentService;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @BeforeAll
    public static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    public static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    private User doctor;

    private User patient;

    private Appointment appointment;

    @BeforeEach
    public void beforeEach() {
        appointmentRepository.deleteAll();
        userRepository.deleteAll();

        doctor = User.builder()
                .name("Doctor Doe")
                .email("doctor@example.com")
                .role(Role.ADMIN)
                .build();
        patient = User.builder()
                .name("John Doe")
                .email("john@example.com")
                .role(Role.USER)
                .build();

        appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStartDateTime(LocalDateTime.now());
        appointment.setEndDateTime(LocalDateTime.now().plusHours(1));
        appointment.setAddress("Ilica 23");

        userRepository.save(doctor);
        userRepository.save(patient);
        appointmentService.saveAppointment(appointment, appointment.getDoctor().getEmail());
    }

    @Test
    void saveAppointment_ValidAppointment_ShouldSaveAppointment() {
        // Assert
        assertNotNull(appointment.getId());
        Optional<Appointment> savedAppointment = appointmentRepository.findById(appointment.getId());
        assertTrue(savedAppointment.isPresent());
    }

    @Test
    void saveAppointment_InvalidAppointment_ShouldNOtSaveAppointment() {
        // Assert
        appointment.setId(2L);
        assertThrows(AppointmentExistsException.class, () -> appointmentService.saveAppointment(appointment, appointment.getDoctor().getEmail()));
    }

    @Test
    void updateAppointment_ValidAppointment_ShouldUpdateAppointment() {
        // Arrange
        appointment.setAddress("Ilica 12345");

        // Act
        appointmentService.saveAppointment(appointment, doctor.getEmail());

        // Assert
        Optional<Appointment> savedAppointment = appointmentRepository.findById(appointment.getId());
        assertTrue(savedAppointment.isPresent());
        assertEquals(savedAppointment.get().getAddress(), appointment.getAddress());
    }


    @Test
    void deleteAppointment_shouldDeleteAppointment() {
        // Arrange
        Long appointmentId = appointment.getId();

        // Act
        appointmentService.deleteAppointment(appointmentId);

        // Assert
        assertFalse(appointmentService.getAppointmentsByUser(appointment.getDoctor().getEmail()).contains(appointment));
    }

    @Test
    void getAppointment_shouldReturnOptionalAppointment() {
        // Arrange
        Long id = appointment.getId();

        // Act
        Optional<Appointment> result = appointmentService.getAppointment(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(appointment.getId(), result.get().getId());
    }

    @Test
    void getAppointmentsByDoctor_shouldReturnListOfAppointments() {
        // Act
        List<Appointment> result = appointmentService.getAppointmentsByUser(doctor.getEmail());

        // Assert
        assertEquals(1, result.size());
        assertEquals(result.getFirst().getDoctor().getEmail(), doctor.getEmail());
    }

    @Test
    void getAppointmentsByUser_shouldReturnListOfAppointments() {
        // Act
        List<Appointment> result = appointmentService.getAppointmentsByUser(patient.getEmail());

        // Assert
        assertEquals(1, result.size());
        assertEquals(result.getFirst().getPatient().getEmail(), patient.getEmail());
    }
}
