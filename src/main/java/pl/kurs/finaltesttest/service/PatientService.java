package pl.kurs.finaltesttest.service;

import pl.kurs.finaltesttest.dto.PatientDTO;

public interface PatientService {
    PatientDTO createPatient(PatientDTO patientDto);

    PatientDTO getPatient(Long id);

    void deletePatient(Long id);

    void updatePatient(Long id, PatientDTO patientDto);
}
