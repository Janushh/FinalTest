package pl.kurs.finaltesttest.mapper;

import org.mapstruct.Mapper;
import pl.kurs.finaltesttest.dto.AppointmentDTO;
import pl.kurs.finaltesttest.model.Appointment;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentDTO toDto(Appointment appointment);

    Appointment toEntity(AppointmentDTO appointmentDTO);
}
