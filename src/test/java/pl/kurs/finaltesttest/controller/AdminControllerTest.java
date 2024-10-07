package pl.kurs.finaltesttest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.kurs.finaltesttest.model.*;
import pl.kurs.finaltesttest.repository.ActionRepository;
import pl.kurs.finaltesttest.repository.AdminRepository;
import pl.kurs.finaltesttest.repository.PatientRepository;

import java.util.Optional;

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
        admin.setPassword("password");
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
        patient.setLocked(false);
        patientRepository.save(patient);

        Admin admin = new Admin();
        admin.setName("Admin");
        admin.setUsername("admin");
        adminRepository.save(admin);

        mockMvc.perform(put("/api/admins/users/{id}/lock", patient.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Patient updatedPatient = patientRepository.findById(patient.getId()).orElseThrow();
        assertTrue(updatedPatient.isLocked());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void shouldAllowAdminToUnlockPatient() throws Exception {
        Patient patient = new Patient();
        patient.setName("Test Patient");
        patient.setPesel("12345678901");
        patient.setLocked(true);
        patientRepository.save(patient);

        Admin admin = new Admin();
        admin.setName("Admin");
        admin.setUsername("admin");
        adminRepository.save(admin);

        mockMvc.perform(put("/api/admins/users/{id}/unlock", patient.getId())
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
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.name").value("New Admin"));

        Optional<Admin> createdAdmin = adminRepository.findByName("New Admin");

        assertTrue(createdAdmin.isPresent(), "Admin should be created and present in the database");

        assertEquals(AdminStatus.ACTIVE, createdAdmin.get().getStatus());
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
                .andExpect(content().string("You cannot remove an admin who has actions assigned to him."));
    }
}
