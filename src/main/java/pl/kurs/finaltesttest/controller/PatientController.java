package pl.kurs.finaltesttest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.finaltesttest.dto.PatientDTO;
import pl.kurs.finaltesttest.service.PatientService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDto) {
        return ResponseEntity.ok(patientService.createPatient(patientDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDto) {
        patientService.updatePatient(id, patientDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatient(id));
    }
}
