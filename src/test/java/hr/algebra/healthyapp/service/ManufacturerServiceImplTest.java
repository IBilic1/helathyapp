package hr.algebra.healthyapp.service;

import hr.algebra.healthyapp.model.Manufacturer;
import hr.algebra.healthyapp.repository.ManufacturerRepository;
import hr.algebra.healthyapp.service.impl.ManufacturerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ManufacturerServiceImplTest {

    @Mock
    ManufacturerRepository manufacturerRepository;

    @InjectMocks
    ManufacturerServiceImpl manufacturerServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveManufacturer() {
        Manufacturer manufacturer = new Manufacturer(1L, "name", "address");

        when(manufacturerRepository.save(any(Manufacturer.class))).thenReturn(any());

        manufacturerServiceImpl.saveManufacturer(manufacturer);
        verify(manufacturerRepository).save(any(Manufacturer.class));
    }

    @Test
    void testDeleteManufacturer() {
        manufacturerServiceImpl.deleteManufacturer(1L);
        verify(manufacturerRepository).deleteById(any(Long.class));
    }

    @Test
    void testGetManufacturer() {
        Manufacturer manufacturer = new Manufacturer(1L, "name", "address");

        when(manufacturerRepository.findById(any(Long.class))).thenReturn(Optional.of(manufacturer));

        Optional<Manufacturer> result = manufacturerServiceImpl.getManufacturer(1L);
        Assertions.assertTrue(result.isPresent());
    }
}
