package pl.kurs.finaltesttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.kurs.finaltesttest.exception.ResourceNotFoundException;
import pl.kurs.finaltesttest.model.Patient;
import pl.kurs.finaltesttest.model.User;
import pl.kurs.finaltesttest.repository.PatientRepository;
import pl.kurs.finaltesttest.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User '" + username + "' not found"));
    }

    public Patient getPatientByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User '" + username + "' not found"));

        return patientRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Patient associated with user '" + username + "' not found"));
    }
}
