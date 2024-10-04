package pl.kurs.finaltesttest.mapper;

import org.mapstruct.Mapper;
import pl.kurs.finaltesttest.dto.PatientDTO;
import pl.kurs.finaltesttest.model.Patient;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTO toDto(Patient patient);

    Patient toEntity(PatientDTO patientDTO);
}
