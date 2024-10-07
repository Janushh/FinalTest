package pl.kurs.finaltesttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.kurs.finaltesttest.dto.AdminDTO;
import pl.kurs.finaltesttest.dto.DoctorDTO;
import pl.kurs.finaltesttest.dto.PatientDTO;
import pl.kurs.finaltesttest.exception.ResourceNotFoundException;
import pl.kurs.finaltesttest.mapper.AdminMapper;
import pl.kurs.finaltesttest.mapper.DoctorMapper;
import pl.kurs.finaltesttest.mapper.PatientMapper;
import pl.kurs.finaltesttest.model.*;
import pl.kurs.finaltesttest.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ActionRepository actionRepository;
    private final PatientRepository patientRepository;
    private final AdminMapper adminMapper;
    private final AdminRepository adminRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorMapper doctorMapper;
    private final PatientMapper patientMapper;
    private final DoctorRepository doctorRepository;

    @Override
    public AdminDTO createAdmin(AdminDTO adminDto) {
        Admin admin = adminMapper.toEntity(adminDto);

        if (admin.getStatus() == null) {
            admin.setStatus(AdminStatus.ACTIVE);
        }

        Admin savedAdmin = adminRepository.save(admin);

        return adminMapper.toDto(savedAdmin);
    }

    @Override
    public void lockUser(Long patientId) {
        changePatientLockStatus(patientId, true, ActionType.LOCKED);
    }

    @Override
    public void unlockUser(Long patientId) {
        changePatientLockStatus(patientId, false, ActionType.UNLOCKED);
    }

    @Override
    public void changePatientLockStatus(Long patientId, boolean lockStatus, ActionType actionType) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        String currentAdminUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Admin admin = adminRepository.findByUsername(currentAdminUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        patient.setLocked(lockStatus);
        patientRepository.save(patient);

        logAction(actionType, patient.getPesel(), admin);
    }


    @Override
    public void updateAppointmentStatus(Long appointmentId, String status, Long adminId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Visit not found"));
        AppointmentStatus newStatus = AppointmentStatus.valueOf(status);
        appointment.setStatus(newStatus);
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Action> getAdminActions() {
        return actionRepository.findAll();
    }


    @Override
    public Admin getAdminById(Long adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .toList();
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    @Override
    public void logAction(ActionType actionType, String field, Admin admin) {
        Action action = new Action();
        action.setActionType(actionType);
        action.setFieldName(field);
        action.setCreatedBy(admin);
        actionRepository.save(action);
    }

    @Override
    public void deleteAdmin(Long adminId) {
        Admin admin = getAdminById(adminId);
        if (!admin.getActions().isEmpty()) {
            throw new IllegalStateException("You cannot remove an admin who has actions assigned to him.");
        }
        adminRepository.delete(admin);
    }

    public String getCurrentAdminUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
