package pl.kurs.finaltesttest.mapper;

import org.mapstruct.Mapper;
import pl.kurs.finaltesttest.dto.DoctorDTO;
import pl.kurs.finaltesttest.model.Doctor;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDTO toDto(Doctor doctor);

    Doctor toEntity(DoctorDTO doctorDTO);
}
