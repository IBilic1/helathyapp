package hr.algebra.healthyapp.model;

import hr.algebra.healthyapp.user.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}
