package pl.kurs.finaltesttest.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.slf4j.Logger;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDto, Long patientId, Long doctorId) {
        Patient patient = validatePatient(patientId);
        Doctor doctor = validateDoctor(doctorId);

        checkIfPatientIsLocked(patient);
        validateAppointmentDate(appointmentDto.getDate());
        checkDoctorAvailability(doctorId, appointmentDto.getDate());

        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }

    public AppointmentDTO createAppointmentForLoggedInUser(AppointmentDTO appointmentDto, Long doctorId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient patient = userService.getPatientByUsername(username);
        return createAppointment(appointmentDto, patient.getId(), doctorId);
    }

    @Override
    public void cancelAppointmentByPatient(Long appointmentId, Long patientId) {
        Appointment appointment = validateAppointment(appointmentId);
        checkPatientAccessToAppointment(appointment, patientId);
        logAndDeleteAppointment(appointmentId, "Patient", patientId);
    }

    public void cancelAppointmentForLoggedInUser(Long appointmentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient patient = userService.getPatientByUsername(username);
        cancelAppointmentByPatient(appointmentId, patient.getId());
    }


    @Override
    public void cancelAppointmentByDoctor(Long appointmentId, Long doctorId) {
        Appointment appointment = validateAppointment(appointmentId);
        checkDoctorAccessToAppointment(appointment, doctorId);
        logAndDeleteAppointment(appointmentId, "Doctor", doctorId);
    }

    private void checkIfPatientIsLocked(Patient patient) {
        if (patient.isLocked()) {
            throw new AccountLockedException("Unable to book an appointment. Patient account is blocked.");
        }
    }

    private void validateAppointmentDate(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Visit date cannot be blank.");
        }
    }

    private void checkDoctorAvailability(Long doctorId, LocalDateTime date) {
        appointmentRepository.findByDoctor_IdAndDate(doctorId, date)
                .ifPresent(a -> {
                    throw new IllegalStateException("The doctor already has an appointment scheduled for that date.");
                });
    }

    private void checkPatientAccessToAppointment(Appointment appointment, Long patientId) {
        if (!appointment.getPatient().getId().equals(patientId)) {
            throw new IllegalArgumentException("The patient does not have the right to cancel this visit.");
        }
    }

    private void checkDoctorAccessToAppointment(Appointment appointment, Long doctorId) {
        if (!appointment.getDoctor().getId().equals(doctorId)) {
            throw new IllegalArgumentException("The Doctor does not have the right to cancel this visit.");
        }
    }

    private void logAndDeleteAppointment(Long appointmentId, String role, Long roleId) {
        logger.info("Cancelling visit ID {} by {} ID {}", appointmentId, role, roleId);
        appointmentRepository.deleteById(appointmentId);
    }

    private Patient validatePatient(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found."));
    }

    private Doctor validateDoctor(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found for ID: " + doctorId));
    }

    private Appointment validateAppointment(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found."));
    }
}
