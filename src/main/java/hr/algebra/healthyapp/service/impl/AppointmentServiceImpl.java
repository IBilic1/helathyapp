package hr.algebra.healthyapp.service.impl;

import hr.algebra.healthyapp.exception.AppointmentExistsException;
import hr.algebra.healthyapp.exception.AppointmentNotFoundException;
import hr.algebra.healthyapp.model.Appointment;
import hr.algebra.healthyapp.model.User;
import hr.algebra.healthyapp.repository.AppointmentRepository;
import hr.algebra.healthyapp.repository.UserRepository;
import hr.algebra.healthyapp.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public void saveAppointment(Appointment appointment, String email) {
        User doctor = userRepository.findByEmail(email).orElseThrow(() ->
                new AppointmentNotFoundException("Doctor with email %s does not exists", appointment.getDoctor().getEmail()));
        User patient = userRepository.findByEmail(appointment.getPatient().getEmail()).orElseThrow(() ->
                new AppointmentNotFoundException("Patient with email %s does not exists", appointment.getPatient().getEmail()));

        long appointmentStartMillis = appointment.getStartDateTime().toInstant(ZoneOffset.UTC).toEpochMilli();
        long appointmentEndMillis = appointment.getEndDateTime().toInstant(ZoneOffset.UTC).toEpochMilli();
        List<Appointment> existingAppointments = getAppointmentsByUser(doctor.getEmail());

        for (Appointment existingAppointment : existingAppointments) {
            if (Objects.equals(appointment.getId(), existingAppointment.getId())) {
                break;
            }
            long existingStartMillis = existingAppointment.getStartDateTime().toInstant(ZoneOffset.UTC).toEpochMilli();
            long existingEndMillis = existingAppointment.getEndDateTime().toInstant(ZoneOffset.UTC).toEpochMilli();

            // Check if there's an overlap
            if (Math.abs(appointmentStartMillis - existingEndMillis) < 30 * 60 * 1000 ||
                    Math.abs(existingStartMillis - appointmentEndMillis) < 30 * 60 * 1000 ||
                    (appointmentStartMillis < existingEndMillis && appointmentEndMillis > existingStartMillis)) {
                throw new AppointmentExistsException("Doctor has an overlapping appointment");
            }
        }

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        Appointment appointmentToDelete = getAppointment(appointmentId).orElseThrow(() ->
                new AppointmentNotFoundException("Appointment id %s does not exists", appointmentId.toString()));
        appointmentRepository.delete(appointmentToDelete);
    }

    @Override
    public Optional<Appointment> getAppointment(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new AppointmentNotFoundException("Doctor with username %s does not exists", email));
        return appointmentRepository.findAllByPatientIdOrDoctorId(user.getId(), user.getId());
    }

    @Override
    public Set<User> getPatientByDoctor(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new AppointmentNotFoundException("Doctor with username %s does not exists", email));
        List<Appointment> allAppointmentsByPatientIdOrDoctorId = appointmentRepository.findAllByPatientIdOrDoctorId(user.getId(), user.getId());

        return allAppointmentsByPatientIdOrDoctorId.stream()
                .filter(appointment -> appointment.getDoctor().getEmail().equals(email))
                .map(Appointment::getPatient)
                .collect(Collectors.toSet());
    }
}
