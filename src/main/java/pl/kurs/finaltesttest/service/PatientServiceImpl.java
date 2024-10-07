package pl.kurs.finaltesttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.finaltesttest.dto.PatientDTO;
import pl.kurs.finaltesttest.exception.ResourceNotFoundException;
import pl.kurs.finaltesttest.mapper.PatientMapper;
import pl.kurs.finaltesttest.model.Patient;
import pl.kurs.finaltesttest.repository.PatientRepository;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public PatientDTO createPatient(PatientDTO patientDto) {
        Patient patient = patientMapper.toEntity(patientDto);
        patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    public PatientDTO getPatient(Long id) {
        return patientRepository.findById(id)
                .map(patientMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public void updatePatient(Long id, PatientDTO patientDto) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        if (patientDto.getName() != null) {
            existingPatient.setName(patientDto.getName());
        }

        if (patientDto.getSurname() != null) {
            existingPatient.setSurname(patientDto.getSurname());
        }

        if (patientDto.getAge() != null && patientDto.getAge() > 0) {
            existingPatient.setAge(patientDto.getAge());
        }

        if (patientDto.getPesel() != null) {
            existingPatient.setPesel(patientDto.getPesel());
        }

        patientRepository.save(existingPatient);
    }
}
