package hr.algebra.healthyapp.repository;

import hr.algebra.healthyapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByPatientIdOrDoctorId(Integer patientId, Integer doctorId);
}
