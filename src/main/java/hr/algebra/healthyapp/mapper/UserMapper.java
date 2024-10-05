package hr.algebra.healthyapp.mapper;

import hr.algebra.healthyapp.dto.UserDto;
import hr.algebra.healthyapp.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {RoleMapper.class})
public interface UserMapper {

    UserDto sourceToDestination(User source);

    User destinationToSource(UserDto destination);

    List<User> sourceToDestination(List<UserDto> destination);

    List<UserDto> destinationToSource(List<User> destination);
}
