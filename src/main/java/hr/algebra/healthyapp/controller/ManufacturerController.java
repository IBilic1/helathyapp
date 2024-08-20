package hr.algebra.healthyapp.controller;

import hr.algebra.healthyapp.dto.ManufacturerDto;
import hr.algebra.healthyapp.mapper.ManufacturerMapper;
import hr.algebra.healthyapp.service.ManufacturerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manufacturer")
@Secured("ADMIN")
public class ManufacturerController {

    private ManufacturerService manufacturerService;

    @Autowired
    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public ResponseEntity<List<ManufacturerDto>> getAllManufacturer() {
        return ResponseEntity.ok().body(ManufacturerMapper.INSTANCE.destinationToSource(manufacturerService.getAllManufacturer()));
    }

    @PostMapping
    public ResponseEntity<Void> createManufacturer(@RequestBody  @Valid ManufacturerDto manufacturer) {
        manufacturerService.saveManufacturer(ManufacturerMapper.INSTANCE.destinationToSource(manufacturer));
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateManufacturer(@RequestBody @Valid ManufacturerDto manufacturer) {
        manufacturerService.saveManufacturer(ManufacturerMapper.INSTANCE.destinationToSource(manufacturer));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{manufacturerId}")
    public ResponseEntity<Void> deleteManufacturer(@PathVariable @Positive Long manufacturerId) {
        manufacturerService.deleteManufacturer(manufacturerId);
        return ResponseEntity.ok().build();
    }
}
