package pl.kurs.finaltesttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.finaltesttest.dto.AppointmentDTO;
import pl.kurs.finaltesttest.dto.DoctorDTO;
import pl.kurs.finaltesttest.dto.PatientDTO;
import pl.kurs.finaltesttest.exception.ResourceNotFoundException;
import pl.kurs.finaltesttest.mapper.AppointmentMapper;
import pl.kurs.finaltesttest.mapper.DoctorMapper;
import pl.kurs.finaltesttest.mapper.PatientMapper;
import pl.kurs.finaltesttest.model.Appointment;
import pl.kurs.finaltesttest.model.Doctor;
import pl.kurs.finaltesttest.model.Patient;
import pl.kurs.finaltesttest.repository.AppointmentRepository;
import pl.kurs.finaltesttest.repository.DoctorRepository;
import pl.kurs.finaltesttest.repository.PatientRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorMapper doctorMapper;
    private final PatientMapper patientMapper;
    private final AppointmentMapper appointmentMapper;
    private final AppointmentServiceImpl appointmentServiceImpl;

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDto) {
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    @Override
    public DoctorDTO getDoctor(Long id) {
        return doctorRepository.findById(id)
                .map(doctorMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Doktor nie znaleziony"));
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public void updateDoctor(Long id, DoctorDTO doctorDto) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        existingDoctor.setName(doctorDto.getName());
        existingDoctor.setSurname(doctorDto.getSurname());
        existingDoctor.setSpecialization(doctorDto.getSpecialization());
        existingDoctor.setAge(doctorDto.getAge());
        doctorRepository.save(existingDoctor);
    }

    @Override
    public List<PatientDTO> getPatientsWithAppointments(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        List<Patient> patients = patientRepository.findDistinctByAppointments_Doctor(doctor);
        return patients.stream().map(patientMapper::toDto).toList();
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDoctor(Long doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctor_Id(doctorId);
        return appointments.stream().map(appointmentMapper::toDto).toList();
    }

    @Override
    public void cancelAppointmentByDoctor(Long appointmentId, Long doctorId) {
        appointmentServiceImpl.cancelAppointmentByDoctor(appointmentId, doctorId);
    }
}
