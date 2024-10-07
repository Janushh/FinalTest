package pl.kurs.finaltesttest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.kurs.finaltesttest.dto.AppointmentDTO;
import pl.kurs.finaltesttest.exception.AccountLockedException;
import pl.kurs.finaltesttest.exception.ResourceNotFoundException;
import pl.kurs.finaltesttest.model.Patient;
import pl.kurs.finaltesttest.service.AppointmentService;
import pl.kurs.finaltesttest.service.AppointmentServiceImpl;
import pl.kurs.finaltesttest.service.UserService;

import java.util.function.Supplier;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestParam Long doctorId, @RequestBody AppointmentDTO appointmentDto) {
        return handleExceptions(() -> {
            AppointmentDTO createdAppointment = appointmentService.createAppointmentForLoggedInUser(appointmentDto, doctorId);
            return ResponseEntity.ok(createdAppointment);
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        return handleExceptions(() -> {
            appointmentService.cancelAppointmentForLoggedInUser(id);
            return ResponseEntity.noContent().build();
        });
    }

    private ResponseEntity<?> handleExceptions(Supplier<ResponseEntity<?>> action) {
        try {
            return action.get();
        } catch (AccountLockedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystąpił błąd serwera.");
        }
    }
}