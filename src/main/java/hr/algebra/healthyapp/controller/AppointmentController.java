package hr.algebra.healthyapp.controller;

import hr.algebra.healthyapp.dto.AppointmentDto;
import hr.algebra.healthyapp.mapper.AppointmentMapper;
import hr.algebra.healthyapp.service.AppointmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
@AllArgsConstructor
@Secured({"USER", "ADMIN"})
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class AppointmentController {

    private AppointmentService appointmentService;

    private AppointmentMapper appointmentMapper;

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByUser(Principal principal) {
        return ResponseEntity.ok(appointmentMapper.destinationToSource(
                appointmentService.getAppointmentsByUser(principal.getName())));
    }

    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<Void> createAppointment(@RequestBody @Valid AppointmentDto appointment, Principal principal) {
        appointmentService.saveAppointment(appointmentMapper.destinationToSource(appointment), principal.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Secured("ADMIN")
    public ResponseEntity<Void> updateAppointment(@RequestBody @Valid AppointmentDto appointment, Principal principal) {
        appointmentService.saveAppointment(appointmentMapper.destinationToSource(appointment), principal.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{appointmentId}")
    @Secured("ADMIN")
    public ResponseEntity<Void> deleteAppointment(@PathVariable @Positive Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.ok().build();
    }
}
