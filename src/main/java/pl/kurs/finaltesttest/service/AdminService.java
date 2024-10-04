package pl.kurs.finaltesttest.service;

import pl.kurs.finaltesttest.dto.AdminDTO;
import pl.kurs.finaltesttest.dto.DoctorDTO;
import pl.kurs.finaltesttest.dto.PatientDTO;
import pl.kurs.finaltesttest.model.Action;
import pl.kurs.finaltesttest.model.ActionType;
import pl.kurs.finaltesttest.model.Admin;

import java.util.List;

public interface AdminService {
    AdminDTO createAdmin(AdminDTO adminDto);

    void lockUser(Long patientId, Long adminId);

    void unlockUser(Long patientId, Long adminId);

    void changePatientLockStatus(Long patientId, Long adminId, boolean lockStatus, ActionType actionType);

    void updateAppointmentStatus(Long appointmentId, String status, Long adminId);

    List<Action> getAdminActions();

    Admin getAdminById(Long adminId);

    List<PatientDTO> getAllPatients();

    List<DoctorDTO> getAllDoctors();

    void logAction(ActionType actionType, String field, Admin admin);

    void deleteAdmin(Long adminId);
}
