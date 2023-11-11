package hr.algebra.healthyapp.repository;

import hr.algebra.healthyapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByPatientId(Integer patientId);

    List<Appointment> findAllByDoctorId(Integer doctorId);
}
