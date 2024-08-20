package hr.algebra.healthyapp.service.impl;

import hr.algebra.healthyapp.model.Medicine;
import hr.algebra.healthyapp.repository.MedicineRepository;
import hr.algebra.healthyapp.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Autowired
    public MedicineServiceImpl(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @Override
    public void saveMedicine(Medicine medicine) {
        medicineRepository.save(medicine);
    }

    @Override
    public void deleteMedicine(Long medicineId) {
        medicineRepository.deleteById(medicineId);
    }

    @Override
    public Optional<Medicine> getMedicine(Long id) {
        return medicineRepository.findById(id);
    }

    @Override
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }
}
