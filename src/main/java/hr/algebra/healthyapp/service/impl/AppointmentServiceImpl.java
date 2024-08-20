package hr.algebra.healthyapp.service.impl;

import hr.algebra.healthyapp.exception.EntityDoesNotExistsException;
import hr.algebra.healthyapp.model.Appointment;
import hr.algebra.healthyapp.model.User;
import hr.algebra.healthyapp.repository.AppointmentRepository;
import hr.algebra.healthyapp.repository.UserRepository;
import hr.algebra.healthyapp.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final UserRepository userRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveAppointment(Appointment appointment, String name) {
        User doctor = userRepository.findByEmail(name).orElseThrow(() ->
                new EntityDoesNotExistsException("Doctor with email %s does not exists", appointment.getDoctor().getEmail()));
        User patient = userRepository.findByEmail(appointment.getPatient().getEmail()).orElseThrow(() ->
                new EntityDoesNotExistsException("Patient with email %s does not exists", appointment.getPatient().getEmail()));
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        Appointment appointmentToDelete = getAppointment(appointmentId).orElseThrow(() ->
                new EntityDoesNotExistsException("Appointment id %s does not exists", appointmentId.toString()));
        appointmentRepository.delete(appointmentToDelete);
    }

    @Override
    public Optional<Appointment> getAppointment(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByUser(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new EntityDoesNotExistsException("Doctor with username %s does not exists", username));
        return appointmentRepository.findAllByPatientIdOrDoctorId(user.getId(), user.getId());
    }
}
