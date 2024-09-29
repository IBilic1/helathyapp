package hr.algebra.healthyapp.mapper;

import hr.algebra.healthyapp.dto.RoleDto;
import hr.algebra.healthyapp.user.Role;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RoleMapper {

    @ValueMapping(target = "DOCTOR", source = "DOCTOR")
    @ValueMapping(target = "PATIENT", source = "PATIENT")
    @ValueMapping(source = "SYSTEM_USER", target = "SYSTEM_USER")
    RoleDto sourceToDestination(Role source);

    @ValueMapping(target = "DOCTOR", source = "DOCTOR")
    @ValueMapping(target = "PATIENT", source = "PATIENT")
    @ValueMapping(target = "SYSTEM_USER", source = "SYSTEM_USER")
    Role destinationToSource(RoleDto source);
}
