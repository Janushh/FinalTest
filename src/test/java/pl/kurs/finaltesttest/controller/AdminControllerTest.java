package pl.kurs.finaltesttest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.kurs.finaltesttest.model.Action;
import pl.kurs.finaltesttest.model.ActionType;
import pl.kurs.finaltesttest.model.Admin;
import pl.kurs.finaltesttest.model.Patient;
import pl.kurs.finaltesttest.repository.ActionRepository;
import pl.kurs.finaltesttest.repository.AdminRepository;
import pl.kurs.finaltesttest.repository.PatientRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ActionRepository actionRepository;

    @BeforeEach
    public void setup() {
        adminRepository.deleteAll();
        patientRepository.deleteAll();
        actionRepository.deleteAll();

        Admin admin = new Admin();
        admin.setName("Admin");
        adminRepository.save(admin);

        Patient patient = new Patient();
        patient.setUsername("active_patient");
        patient.setLocked(false);
        patientRepository.save(patient);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void shouldAllowAdminToLockPatient() throws Exception {
        Patient patient = new Patient();
        patient.setName("Testowy Pacjent");
        patient.setPesel("12345678901");
        patientRepository.save(patient);

        Admin admin = new Admin();
        admin.setName("Admin");
        adminRepository.save(admin);

        mockMvc.perform(put("/api/admins/users/{id}/lock", patient.getId())
                        .param("adminId", admin.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Patient updatedPatient = patientRepository.findById(patient.getId()).orElseThrow();
        assertTrue(updatedPatient.isLocked());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void shouldAllowAdminToUnlockPatient() throws Exception {
        Patient patient = new Patient();
        patient.setName("Testowy Pacjent");
        patient.setPesel("12345678901");
        patient.setLocked(true);
        patientRepository.save(patient);

        Admin admin = new Admin();
        admin.setName("Admin");
        adminRepository.save(admin);

        mockMvc.perform(put("/api/admins/users/{id}/unlock", patient.getId())
                        .param("adminId", admin.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Patient updatedPatient = patientRepository.findById(patient.getId()).orElseThrow();
        assertFalse(updatedPatient.isLocked());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void shouldCreateAdminSuccessfully() throws Exception {
        String adminJson = "{ \"name\": \"New Admin\", \"status\": \"ACTIVE\" }";

        mockMvc.perform(post("/api/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adminJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void shouldNotDeleteAdminWithActions() throws Exception {
        Admin admin = new Admin();
        admin.setName("Admin");
        adminRepository.save(admin);

        Action action = new Action();
        action.setActionType(ActionType.LOCKED);
        action.setCreatedBy(admin);
        actionRepository.save(action);

        mockMvc.perform(delete("/api/admins/{id}", admin.getId()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Nie można usunąć admina, który ma przypisane akcje."));
    }
}
