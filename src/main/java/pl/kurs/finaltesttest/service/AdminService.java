package pl.kurs.finaltesttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.finaltesttest.dto.AdminDTO;
import pl.kurs.finaltesttest.exception.ResourceNotFoundException;
import pl.kurs.finaltesttest.mapper.AdminMapper;
import pl.kurs.finaltesttest.model.Action;
import pl.kurs.finaltesttest.model.ActionType;
import pl.kurs.finaltesttest.model.Admin;
import pl.kurs.finaltesttest.model.Patient;
import pl.kurs.finaltesttest.repository.ActionRepository;
import pl.kurs.finaltesttest.repository.AdminRepository;
import pl.kurs.finaltesttest.repository.DoctorRepository;
import pl.kurs.finaltesttest.repository.PatientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ActionRepository actionRepository;
    private final PatientRepository patientRepository;
    private final AdminMapper adminMapper;
    private final AdminRepository adminRepository;

    public AdminDTO createAdmin(AdminDTO adminDto) {
        Admin admin = adminMapper.toEntity(adminDto);
        adminRepository.save(admin);
        return adminMapper.toDto(admin);
    }

    public void lockUser(Long patientId, Long adminId) {
        // Znalezienie pacjenta
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Pacjent nie znaleziony"));
        patient.setLocked(true);
        patientRepository.save(patient);

        // Znalezienie administratora, który wykonuje akcję
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin nie znaleziony"));

        // Zapisanie akcji
        logAction(ActionType.LOCKED, patient.getPesel(), admin);
    }

    public void unlockUser(Long patientId, Long adminId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Pacjent nie znaleziony"));
        patient.setLocked(false);
        patientRepository.save(patient);

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin nie znaleziony"));

        logAction(ActionType.UNLOCKED, patient.getPesel(), admin);
    }

    private void logAction(ActionType actionType, String pesel, Admin admin) {
        Action action = new Action();
        action.setActionType(actionType);
        action.setFieldName("Pesel");
        action.setOldValue(pesel);
        action.setCreatedBy(admin); // Przypisanie administratora
        actionRepository.save(action);
    }

    public List<Action> getAdminActions() {
        return actionRepository.findAll();
    }
}
