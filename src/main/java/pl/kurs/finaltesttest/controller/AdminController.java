package pl.kurs.finaltesttest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.kurs.finaltesttest.dto.AdminDTO;
import pl.kurs.finaltesttest.dto.DoctorDTO;
import pl.kurs.finaltesttest.dto.PatientDTO;
import pl.kurs.finaltesttest.model.Action;
import pl.kurs.finaltesttest.model.AppointmentStatus;
import pl.kurs.finaltesttest.service.AdminService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminDTO adminDto) {
        return ResponseEntity.ok(adminService.createAdmin(adminDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @Secured("ADMIN")
    @PutMapping("/users/{id}/lock")
    public ResponseEntity<Void> lockUser(@PathVariable Long id) {
        adminService.lockUser(id);
        return ResponseEntity.noContent().build();
    }

    @Secured("ADMIN")
    @PutMapping("/users/{id}/unlock")
    public ResponseEntity<Void> unlockUser(@PathVariable Long id) {
        adminService.unlockUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/actions")
    public ResponseEntity<List<Action>> getAdminActions() {
        return ResponseEntity.ok(adminService.getAdminActions());
    }

    @PutMapping("/appointments/{id}/status")
    public ResponseEntity<Void> updateAppointmentStatus(@PathVariable Long id, @RequestBody AppointmentStatus appointmentStatus, @RequestParam Long adminId) {
        adminService.updateAppointmentStatus(id, appointmentStatus.name(), adminId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(adminService.getAllPatients());
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(adminService.getAllDoctors());
    }
}
