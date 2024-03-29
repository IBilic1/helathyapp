package hr.algebra.healthyapp.repository;

import hr.algebra.healthyapp.model.Medicine;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository {

    Long saveMedicine(Medicine medicine);

    void deleteMedicine(Long medicineId);

    Optional<Medicine> getMedicine(Long id);

    List<Medicine> getAllMedicine();

    List<Medicine> getMedicineByManufacturer(Long manufacturerId);

    void batchUpdateMedicine(List<Medicine> medicines);
}
