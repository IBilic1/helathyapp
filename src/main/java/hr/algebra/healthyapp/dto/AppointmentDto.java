package hr.algebra.healthyapp.dto;

import hr.algebra.healthyapp.validator.EndDateAfterStartDate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EndDateAfterStartDate
public class AppointmentDto {

    private Long id;

    @NotNull(message = "Start date and time must not be null")
    @FutureOrPresent(message = "Start date and time must be in the future or present")
    private LocalDateTime startDateTime;

    @NotNull(message = "End date and time must not be null")
    @FutureOrPresent(message = "End date and time must be in the future or present")
    private LocalDateTime endDateTime;

    @NotBlank(message = "Address must not be blank")
    private String address;

    private UserDto doctor;

    private UserDto patient;
}