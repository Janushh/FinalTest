package pl.kurs.finaltesttest.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.kurs.finaltesttest.model.Patient;

public interface UserService {
    UserDetailsService userDetailsService();

    Patient getPatientByUsername(String username);
}
