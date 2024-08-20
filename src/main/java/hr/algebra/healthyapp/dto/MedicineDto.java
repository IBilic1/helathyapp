package hr.algebra.healthyapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Positive
    private Long manufacturerId;
}
