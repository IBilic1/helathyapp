package hr.algebra.healthyapp.controller;

import hr.algebra.healthyapp.auth.CustomOAuth2User;
import hr.algebra.healthyapp.dto.UserDto;
import hr.algebra.healthyapp.mapper.AppointmentMapper;
import hr.algebra.healthyapp.mapper.UserMapper;
import hr.algebra.healthyapp.model.User;
import hr.algebra.healthyapp.service.AppointmentService;
import hr.algebra.healthyapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private AppointmentService appointmentService;

    private UserMapper userMapper;


    @GetMapping
    @Secured("DOCTOR")
    public ResponseEntity<List<UserDto>> getUsersByDoctor(@AuthenticationPrincipal CustomOAuth2User principal) {
        Set<User> patients = appointmentService.getPatientByDoctor(principal.getEmail());
        return ResponseEntity.ok(userMapper.destinationToSource(patients.stream().toList()));
    }

    @GetMapping("/patient/all")
    @Secured({"DOCTOR", "SYSTEM_USER"})
    public ResponseEntity<List<UserDto>> getAllPatients() {
        return ResponseEntity.ok(userMapper.destinationToSource(userService.getAllPatients()));
    }

    @GetMapping("/all")
    @Secured({"DOCTOR", "SYSTEM_USER"})
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userMapper.destinationToSource(userService.getAllUsers()));
    }


    @GetMapping("/user-info")
    public ResponseEntity<UserDto> getAllUsers(@AuthenticationPrincipal CustomOAuth2User principal) {
        return ResponseEntity.of(userService.getUser(principal.getEmail()).map(userMapper::sourceToDestination));
    }

    @PatchMapping
    @Secured("SYSTEM_USER")
    public ResponseEntity<UserDto> changeAuthority(@RequestBody UserDto user) {
        User userToUpdate = userMapper.destinationToSource(user);
        return ResponseEntity.of(userService.changeAuthority(userToUpdate).map(userMapper::sourceToDestination));
    }
}
