package pl.kurs.finaltesttest.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.finaltesttest.dto.AppointmentDTO;
import pl.kurs.finaltesttest.service.AppointmentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestParam Long patientId, @RequestParam Long doctorId, @RequestBody AppointmentDTO appointmentDto) {

        return ResponseEntity.ok(appointmentService.createAppointment(appointmentDto, patientId, doctorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
