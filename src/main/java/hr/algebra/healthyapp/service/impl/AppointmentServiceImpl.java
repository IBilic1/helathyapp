package hr.algebra.healthyapp.service.impl;

import hr.algebra.healthyapp.model.Appointment;
import hr.algebra.healthyapp.repository.AppointmentRepository;
import hr.algebra.healthyapp.repository.UserRepository;
import hr.algebra.healthyapp.service.AppointmentService;
import hr.algebra.healthyapp.model.User;
import hr.algebra.healthyapp.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private AppointmentRepository appointmentRepository;

    private UserRepository userRepository;

    @Override
    public void saveAppointment(Appointment appointment, String name) {
        User doctor = userRepository.findByEmail(name).orElseThrow(RuntimeException::new);
        User patient = userRepository.findByEmail(appointment.getPatient().getEmail()).orElseThrow(RuntimeException::new);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        Appointment appointmentToDelete = getAppointment(appointmentId).orElseThrow(RuntimeException::new);
        appointmentRepository.delete(appointmentToDelete);
    }

    @Override
    public Optional<Appointment> getAppointment(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByUser(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(RuntimeException::new);
        return appointmentRepository.findAllByPatientId(user.getId());
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(RuntimeException::new);
        if(user.getRole() == Role.ADMIN){
            return appointmentRepository.findAllByDoctorId(user.getId());
        }
        return appointmentRepository.findAllByPatientId(user.getId());
    }
}
