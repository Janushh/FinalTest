package pl.kurs.finaltesttest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.finaltesttest.dto.AdminDTO;
import pl.kurs.finaltesttest.model.Action;
import pl.kurs.finaltesttest.service.AdminService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminDTO adminDto) {
        return ResponseEntity.ok(adminService.createAdmin(adminDto));
    }

    @PutMapping("/users/{id}/lock")
    public ResponseEntity<Void> lockUser(@PathVariable Long id, @RequestParam Long adminId) {
        adminService.lockUser(id, adminId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/unlock")
    public ResponseEntity<Void> unlockUser(@PathVariable Long id, @RequestParam Long adminId) {
        adminService.unlockUser(id, adminId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/actions")
    public ResponseEntity<List<Action>> getAdminActions() {
        return ResponseEntity.ok(adminService.getAdminActions());
    }
}
