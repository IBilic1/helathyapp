package hr.algebra.healthyapp.repository;

import hr.algebra.healthyapp.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByPatientIdOrDoctorId(Integer patientId, Integer doctorId);
}
