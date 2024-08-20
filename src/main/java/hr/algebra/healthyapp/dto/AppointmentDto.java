package hr.algebra.healthyapp.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {

    private Long id;

    @NotNull(message = "1")
    @PastOrPresent(message = "2")
    private LocalDateTime startDateTime;

    @NotNull(message = "3")
    @PastOrPresent(message = "4")
    private LocalDateTime endDateTime;

    @NotBlank(message = "5")
    private String address;

    private UserDto doctor;

    private UserDto patient;
}
