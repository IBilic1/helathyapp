package hr.algebra.healthyapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    private RoleDto role;
}
