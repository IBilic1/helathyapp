package hr.algebra.healthyapp.validator;


import hr.algebra.healthyapp.dto.AppointmentDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EndDateAfterStartDateValidator implements ConstraintValidator<EndDateAfterStartDate, AppointmentDto> {

    @Override
    public boolean isValid(AppointmentDto appointmentDto, ConstraintValidatorContext context) {
        if (appointmentDto.getStartDateTime() == null || appointmentDto.getEndDateTime() == null) {
            return true;
        }
        return appointmentDto.getEndDateTime().isAfter(appointmentDto.getStartDateTime());
    }
}
