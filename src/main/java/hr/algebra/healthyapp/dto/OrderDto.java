package hr.algebra.healthyapp.dto;

import hr.algebra.healthyapp.model.Medicine;
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

    private String description;

    private Double amount;

    private Double doseGap;

    private MedicineDto medicine;
}
