package hr.algebra.healthyapp.service;

import hr.algebra.healthyapp.model.Order;
import hr.algebra.healthyapp.model.Prescription;
import hr.algebra.healthyapp.model.User;
import hr.algebra.healthyapp.repository.MedicineRepository;
import hr.algebra.healthyapp.repository.PrescriptionRepository;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("unit")
public class PrescriptionServiceTest {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PrescriptionService prescriptionService;

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

    private Prescription prescription;

    private User doctor;

    private User patient;

    @BeforeEach
    public void beforeEach() {
        prescriptionRepository.deleteAll();
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

        prescription = Prescription.builder()
                .patient(patient)
                .doctor(doctor)
                .build();

        userRepository.save(doctor);
        userRepository.save(patient);
        prescriptionRepository.save(prescription);
    }

    @Test
    void savePrescription_ValidPrescription_ShouldSavePrescription() {
        // Act
        Prescription newPrescription = prescriptionService.savePrescription(this.prescription, prescription.getDoctor().getEmail());

        // Assert
        assertNotNull(this.prescription.getId());
        Optional<Prescription> savedPrescription = prescriptionService.getPrescription(newPrescription.getId());
        assertTrue(savedPrescription.isPresent());
    }

    @Test
    void updatePrescription_ValidPrescription_ShouldUpdatePrescription() {
        // Arrange
        Order order = Order.builder()
                .amount(10D)
                .description("DESC")
                .doseGap(5D)
                .build();
        Prescription prescriptionToUpdate = Prescription.builder()
                .doctor(doctor)
                .patient(patient)
                .orders(Collections.singletonList(order)).build();
        // Act
        Prescription updatedPrescription = prescriptionService.savePrescription(prescriptionToUpdate, this.prescription.getDoctor().getEmail());

        // Assert
        Optional<Prescription> savedPrescription = prescriptionService.getPrescription(updatedPrescription.getId());
        assertTrue(savedPrescription.isPresent());
        List<Order> orders = savedPrescription.get().getOrders();
        assertEquals(orders.size(), 1);
    }


    @Test
    void deleteAppointment_shouldDeleteAppointment() {
        // Arrange
        prescriptionService.savePrescription(prescription, prescription.getDoctor().getEmail());

        // Act
        prescriptionService.deletePrescription(prescription.getId());

        // Assert
        assertFalse(prescriptionService.getPrescriptionsByUser(prescription.getDoctor().getEmail()).contains(prescription));
    }

    @Test
    void getAppointment_shouldReturnOptionalAppointment() {
        // Arrange
        Prescription newPrescription = prescriptionService.savePrescription(this.prescription, doctor.getEmail());

        // Act
        Optional<Prescription> result = prescriptionService.getPrescription(newPrescription.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(prescription.getId(), result.get().getId());
    }

    @Test
    void getPrescriptionByDoctor_shouldReturnListOfPrescription() {
        // Act
        List<Prescription> result = prescriptionService.getPrescriptionsByUser(doctor.getEmail());

        // Assert
        assertEquals(1, result.size());
        assertEquals(result.getFirst().getDoctor().getEmail(), doctor.getEmail());
    }

    @Test
    void getPrescriptionByUser_shouldReturnListOfPrescription() {
        // Act
        List<Prescription> result = prescriptionService.getPrescriptionsByUser(patient.getEmail());

        // Assert
        assertEquals(1, result.size());
        assertEquals(result.getFirst().getPatient().getEmail(), patient.getEmail());
    }
}
