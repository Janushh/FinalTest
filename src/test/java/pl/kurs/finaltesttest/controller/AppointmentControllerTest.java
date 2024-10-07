package pl.kurs.finaltesttest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.kurs.finaltesttest.model.Appointment;
import pl.kurs.finaltesttest.model.Doctor;
import pl.kurs.finaltesttest.model.Patient;
import pl.kurs.finaltesttest.repository.AppointmentRepository;
import pl.kurs.finaltesttest.repository.DoctorRepository;
import pl.kurs.finaltesttest.repository.PatientRepository;
import pl.kurs.finaltesttest.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    private Doctor savedDoctor;
    private Patient savedPatient;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();

        Patient patient = new Patient();
        patient.setUsername("active_patient");
        patient.setLocked(false);
        savedPatient = patientRepository.save(patient);

        Doctor doctor = new Doctor();
        doctor.setName("Jan");
        doctor.setSurname("Kowalski");
        doctor.setSpecialization("Kardiolog");
        doctor.setAge(45);
        savedDoctor = doctorRepository.save(doctor);
    }

    @Test
    @WithMockUser(username = "active_patient", roles = {"PATIENT"})
    public void shouldAllowActivePatientToCreateAppointment() throws Exception {
        mockMvc.perform(post("/api/appointments")
                        .param("doctorId", savedDoctor.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"date\": \"2024-12-12T10:00:00\" }"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "active_patient", roles = {"PATIENT"})
    public void shouldAllowPatientToCancelOwnAppointment() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setDoctor(savedDoctor);
        appointment.setPatient(savedPatient);
        appointment.setDate(LocalDateTime.now().plusDays(1));
        Appointment savedAppointment = appointmentRepository.save(appointment);

        mockMvc.perform(delete("/api/appointments/{id}", savedAppointment.getId()))
                .andExpect(status().isNoContent());

        assertFalse(appointmentRepository.findById(savedAppointment.getId()).isPresent());
    }

    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void shouldAllowDoctorToCancelAppointment() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setPatient(savedPatient);
        appointment.setDoctor(savedDoctor);
        appointment.setDate(LocalDateTime.now().plusDays(1));
        Appointment savedAppointment = appointmentRepository.save(appointment);

        mockMvc.perform(delete("/api/doctors/{id}/appointments/{appointmentId}", savedDoctor.getId(), savedAppointment.getId()))
                .andExpect(status().isNoContent());

        assertFalse(appointmentRepository.findById(savedAppointment.getId()).isPresent());
    }

    @Test
    @WithMockUser(username = "active_patient", roles = {"PATIENT"})
    public void shouldNotAllowLockedPatientToCreateAppointment() throws Exception {
        savedPatient.setLocked(true);

        if (savedPatient.getAppointments() == null) {
            savedPatient.setAppointments(new ArrayList<>());
        } else {
            savedPatient.setAppointments(savedPatient.getAppointments());
        }

        patientRepository.save(savedPatient);

        mockMvc.perform(post("/api/appointments")
                        .param("doctorId", savedDoctor.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"date\": \"2024-12-12T10:00:00\" }"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "patient", roles = {"PATIENT"})
    public void shouldThrowExceptionWhenPatientNotFound() throws Exception {
        mockMvc.perform(post("/api/appointments")
                        .param("doctorId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"date\": \"2024-12-12T10:00:00\" }"))
                .andExpect(status().isNotFound());
    }
}