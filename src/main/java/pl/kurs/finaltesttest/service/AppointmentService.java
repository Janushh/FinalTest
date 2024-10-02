package pl.kurs.finaltesttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.finaltesttest.dto.AppointmentDTO;
import pl.kurs.finaltesttest.exception.AccountLockedException;
import pl.kurs.finaltesttest.exception.ResourceNotFoundException;
import pl.kurs.finaltesttest.mapper.AppointmentMapper;
import pl.kurs.finaltesttest.model.Appointment;
import pl.kurs.finaltesttest.model.Doctor;
import pl.kurs.finaltesttest.model.Patient;
import pl.kurs.finaltesttest.repository.AppointmentRepository;
import pl.kurs.finaltesttest.repository.DoctorRepository;
import pl.kurs.finaltesttest.repository.PatientRepository;


@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentDTO createAppointment(AppointmentDTO appointmentDto, Long patientId, Long doctorId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Pacjent nie znaleziony"));
        if (patient.isLocked()) {
            throw new AccountLockedException("Konto pacjenta jest zablokowane");
        }

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doktor nie znaleziony"));

        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(appointmentDto.getDate());

        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }

    public void cancelAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
