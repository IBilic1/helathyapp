package hr.algebra.healthyapp.controller;

import hr.algebra.healthyapp.dto.PrescriptionDto;
import hr.algebra.healthyapp.mapper.PrescriptionMapper;
import hr.algebra.healthyapp.service.PrescriptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    private PrescriptionService prescriptionService;

    private PrescriptionMapper prescriptionMapper;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, PrescriptionMapper prescriptionMapper) {
        this.prescriptionService = prescriptionService;
        this.prescriptionMapper = prescriptionMapper;
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionDto>> getPrescriptionsByDoctor(Principal principal) {
        return ResponseEntity.ok(prescriptionMapper.destinationToSource(
                prescriptionService.getPrescriptionsByUser(principal.getName())));
    }

    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<Void> createPrescription(@RequestBody @Valid PrescriptionDto prescription, Principal principal) {
        prescriptionService.savePrescription(
                prescriptionMapper.destinationToSource(prescription), principal.getName());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Secured("ADMIN")
    public ResponseEntity<Void> updateAppointment(@RequestBody @Valid PrescriptionDto prescription, Principal principal) {
        prescriptionService.savePrescription(
                prescriptionMapper.destinationToSource(prescription), principal.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{prescriptionId}")
    @Secured("ADMIN")
    public ResponseEntity<Void> deleteAppointment(@PathVariable @Positive Long prescriptionId) {
        prescriptionService.deletePrescription(prescriptionId);
        return ResponseEntity.ok().build();
    }
}
