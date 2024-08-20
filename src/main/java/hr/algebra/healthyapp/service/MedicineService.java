package hr.algebra.healthyapp.service;

import hr.algebra.healthyapp.model.Medicine;

import java.util.List;
import java.util.Optional;

public interface MedicineService {

    void saveMedicine(Medicine medicine);

    void deleteMedicine(Long Medicine);

    Optional<Medicine> getMedicine(Long id);

    List<Medicine> getAllMedicines();
}
