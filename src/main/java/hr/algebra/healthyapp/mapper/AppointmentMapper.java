package hr.algebra.healthyapp.mapper;

import hr.algebra.healthyapp.dto.AppointmentDto;
import hr.algebra.healthyapp.model.Appointment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserMapper.class})
public interface AppointmentMapper {

    AppointmentDto sourceToDestination(Appointment source);

    Appointment destinationToSource(AppointmentDto destination);

    List<Appointment> sourceToDestination(List<AppointmentDto> destination);

    List<AppointmentDto> destinationToSource(List<Appointment> destination);
}
