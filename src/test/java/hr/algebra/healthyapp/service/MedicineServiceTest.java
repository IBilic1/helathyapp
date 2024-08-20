package hr.algebra.healthyapp.service;

import hr.algebra.healthyapp.model.Manufacturer;
import hr.algebra.healthyapp.model.Medicine;
import hr.algebra.healthyapp.repository.ManufacturerRepository;
import hr.algebra.healthyapp.repository.MedicineRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("unit")
public class MedicineServiceTest {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private MedicineService medicineService;

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

    private Medicine medicine;

    @BeforeEach
    public void beforeEach() {
        Manufacturer manufacturer = new Manufacturer(1L, "Manufacturer 01", "Address 01");
        medicine = Medicine.builder()
                .description("Desc")
                .manufacturer(manufacturer)
                .name("Brufen")
                .build();

        manufacturerRepository.save(manufacturer);
        medicineRepository.save(medicine);
    }

    @Test
    void saveMedicine_ValidMedicine_ShouldSaveMedicine() {
        // Act
        Long id = medicineRepository.save(medicine).getId();

        // Assert
        assertNotNull(id);
        Optional<Medicine> savedAppointment = medicineRepository.findById(id);
        assertTrue(savedAppointment.isPresent());
    }

    @Test
    void updateMedicine_ValidMedicine_ShouldUpdateMedicine() {
        // Arrange
        Medicine medicineToUpdate = Medicine.builder()
                .description("Desc")
                .manufacturer(new Manufacturer(1L, "", ""))
                .name("Brufen")
                .build();
        ;
        medicineToUpdate.setDescription("DESC UPDATED");
        medicineToUpdate.setId(1L);

        Long id = medicineRepository.save(medicineToUpdate).getId();

        Optional<Medicine> savedAppointment = medicineRepository.findById(id);
        assertTrue(savedAppointment.isPresent());
        assertEquals(savedAppointment.get().getDescription(), medicineToUpdate.getDescription());
    }

    @Test()
    void deleteMedicine_shouldNotDeleteMedicine() {
        // Arrange
        Long id = medicineRepository.save(medicine).getId();

        // Act
        medicineService.deleteMedicine(id);

        // Assert
        assertFalse(medicineService.getMedicine(id).isPresent());
    }

    @Test
    void getMedicine_shouldReturnOptionalMedicine() {
        // Arrange
        Long id = medicineRepository.save(medicine).getId();
        medicine.setId(id);

        // Act
        Optional<Medicine> result = medicineService.getMedicine(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(medicine, result.get());
    }
}
