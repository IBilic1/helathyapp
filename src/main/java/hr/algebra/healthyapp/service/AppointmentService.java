package hr.algebra.healthyapp.service;

import hr.algebra.healthyapp.model.Appointment;

import java.util.List;
import java.util.Optional;


public interface AppointmentService {

    void saveAppointment(Appointment appointment, String name);

    void deleteAppointment( Long appointmentId);

    Optional<Appointment> getAppointment(Long id);

    List<Appointment> getAppointmentsByUser(String username);
}
