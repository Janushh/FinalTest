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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    public void setup() {
        appointmentRepository.deleteAll();
        doctorRepository.deleteAll();
        patientRepository.deleteAll();

        Doctor doctor = new Doctor();
        doctor.setName("Jan");
        doctor.setSurname("Kowalski");
        doctor.setSpecialization("Kardiolog");
        doctor.setAge(45);
        doctorRepository.save(doctor);

        Patient patient = new Patient();
        patient.setUsername("patient1");
        patientRepository.save(patient);
    }

    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void shouldAllowDoctorToViewPatientsWithAppointments() throws Exception {
        Doctor doctor = doctorRepository.findAll().get(0);
        Patient patient = patientRepository.findAll().get(0);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(LocalDateTime.now().plusDays(1));
        appointmentRepository.save(appointment);

        mockMvc.perform(get("/api/doctors/{id}/patients", doctor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(patient.getId()));
    }
}