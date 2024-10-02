package pl.kurs.finaltesttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.finaltesttest.dto.DoctorDTO;
import pl.kurs.finaltesttest.exception.ResourceNotFoundException;
import pl.kurs.finaltesttest.mapper.DoctorMapper;
import pl.kurs.finaltesttest.model.Doctor;
import pl.kurs.finaltesttest.repository.DoctorRepository;


@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorDTO createDoctor(DoctorDTO doctorDto) {
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    public DoctorDTO getDoctor(Long id) {
        return doctorRepository.findById(id)
                .map(doctorMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Doktor nie znaleziony"));
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public void updateDoctor(Long id, DoctorDTO doctorDto) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doktor nie znaleziony"));
        existingDoctor.setName(doctorDto.getName());
        existingDoctor.setSurname(doctorDto.getSurname());
        existingDoctor.setSpecialization(doctorDto.getSpecialization());
        existingDoctor.setAge(doctorDto.getAge());
        doctorRepository.save(existingDoctor);
    }
}
