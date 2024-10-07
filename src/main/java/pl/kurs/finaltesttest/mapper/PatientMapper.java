package pl.kurs.finaltesttest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kurs.finaltesttest.dto.PatientDTO;
import pl.kurs.finaltesttest.model.Patient;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "age", source = "age")
    PatientDTO toDto(Patient patient);

    @Mapping(target = "age", source = "age")
    Patient toEntity(PatientDTO patientDTO);
}
