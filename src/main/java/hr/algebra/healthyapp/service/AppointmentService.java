package hr.algebra.healthyapp.service;

import hr.algebra.healthyapp.model.Appointment;
import hr.algebra.healthyapp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface AppointmentService {

    void saveAppointment(Appointment appointment, String email);

    void deleteAppointment(Long appointmentId);

    Optional<Appointment> getAppointment(Long id);

    List<Appointment> getAppointmentsByUser(String email);

    Set<User> getPatientByDoctor(String email);
}
