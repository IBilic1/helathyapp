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
public class OrderDto {

    private Long id;

    @NotBlank
    private String description;

    @Positive
    private Double amount;

    @Positive
    private Double doseGap;

    private MedicineDto medicine;
}
