package pl.kurs.finaltesttest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.finaltesttest.dto.DoctorDTO;
import pl.kurs.finaltesttest.service.DoctorService;


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
}

