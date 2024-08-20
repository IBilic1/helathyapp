package hr.algebra.healthyapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDto {

    private Long id;

    private UserDto patient;

    private UserDto doctor;

    private List<OrderDto> orders;
}
