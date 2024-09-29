package hr.algebra.healthyapp.mapper;

import hr.algebra.healthyapp.dto.RoleDto;
import hr.algebra.healthyapp.user.Role;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @ValueMapping(target = "ADMIN", source = "ADMIN")
    @ValueMapping(target = "USER", source = "USER")
    @ValueMapping(source = "SYSTEM_USER", target = MappingConstants.NULL)
    RoleDto sourceToDestination(Role source);

    @ValueMapping(target = "ADMIN", source = "ADMIN")
    @ValueMapping(target = "USER", source = "USER")
    @ValueMapping(target = "SYSTEM_USER", source = MappingConstants.NULL)
    Role destinationToSource(RoleDto source);
}
