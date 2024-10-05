package hr.algebra.healthyapp.controller;

import hr.algebra.healthyapp.auth.CustomOAuth2User;
import hr.algebra.healthyapp.dto.AppointmentDto;
import hr.algebra.healthyapp.mapper.AppointmentMapper;
import hr.algebra.healthyapp.model.Appointment;
import hr.algebra.healthyapp.service.AppointmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@AllArgsConstructor
@Secured({"PATIENT", "DOCTOR"})
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class AppointmentController {

    private AppointmentService appointmentService;

    private AppointmentMapper appointmentMapper;

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByUser(@AuthenticationPrincipal CustomOAuth2User principal) {
        List<Appointment> appointmentsByUser = appointmentService.getAppointmentsByUser(principal.getEmail());
        return ResponseEntity.ok(appointmentMapper.destinationToSource(appointmentsByUser));
    }

    @PostMapping
    @Secured("DOCTOR")
    public ResponseEntity<Void> createAppointment(@RequestBody @Valid AppointmentDto appointment, @AuthenticationPrincipal CustomOAuth2User principal) {
        appointmentService.saveAppointment(appointmentMapper.destinationToSource(appointment), principal.getEmail());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Secured("DOCTOR")
    public ResponseEntity<Void> updateAppointment(@RequestBody @Valid AppointmentDto appointment, @AuthenticationPrincipal CustomOAuth2User principal) {
        appointmentService.saveAppointment(appointmentMapper.destinationToSource(appointment), principal.getEmail());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{appointmentId}")
    @Secured("DOCTOR")
    public ResponseEntity<Void> deleteAppointment(@PathVariable @Positive Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.ok().build();
    }
}
