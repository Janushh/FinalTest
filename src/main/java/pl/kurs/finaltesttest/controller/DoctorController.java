package pl.kurs.finaltesttest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.finaltesttest.dto.AppointmentDTO;
import pl.kurs.finaltesttest.dto.DoctorDTO;
import pl.kurs.finaltesttest.dto.PatientDTO;
import pl.kurs.finaltesttest.service.DoctorService;
import pl.kurs.finaltesttest.service.DoctorServiceImpl;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDto) {
        return ResponseEntity.ok(doctorService.createDoctor(doctorDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctor(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDto) {
        doctorService.updateDoctor(id, doctorDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/appointments/{appointmentId}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id, @PathVariable Long appointmentId) {
        doctorService.cancelAppointmentByDoctor(appointmentId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/patients")
    public ResponseEntity<List<PatientDTO>> getPatientsWithAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getPatientsWithAppointments(id));
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDoctor(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getAppointmentsByDoctor(id));
    }
}
