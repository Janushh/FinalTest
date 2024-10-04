package pl.kurs.finaltesttest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.finaltesttest.dto.AdminDTO;
import pl.kurs.finaltesttest.dto.DoctorDTO;
import pl.kurs.finaltesttest.dto.PatientDTO;
import pl.kurs.finaltesttest.dto.UpdateStatusDTO;
import pl.kurs.finaltesttest.model.Action;
import pl.kurs.finaltesttest.service.AdminServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminServiceImpl adminServiceImpl;

    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminDTO adminDto) {
        return ResponseEntity.ok(adminServiceImpl.createAdmin(adminDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminServiceImpl.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/lock")
    public ResponseEntity<Void> lockUser(@PathVariable Long id, @RequestParam Long adminId) {
        adminServiceImpl.lockUser(id, adminId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/unlock")
    public ResponseEntity<Void> unlockUser(@PathVariable Long id, @RequestParam Long adminId) {
        adminServiceImpl.unlockUser(id, adminId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/actions")
    public ResponseEntity<List<Action>> getAdminActions() {
        return ResponseEntity.ok(adminServiceImpl.getAdminActions());
    }

    @PutMapping("/appointments/{id}/status")
    public ResponseEntity<Void> updateAppointmentStatus(@PathVariable Long id, @RequestBody UpdateStatusDTO statusDTO, @RequestParam Long adminId) {
        adminServiceImpl.updateAppointmentStatus(id, statusDTO.getStatus(), adminId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(adminServiceImpl.getAllPatients());
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(adminServiceImpl.getAllDoctors());
    }
}
