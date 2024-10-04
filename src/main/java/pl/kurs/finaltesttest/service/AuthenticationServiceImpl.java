package pl.kurs.finaltesttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kurs.finaltesttest.command.SignInRequest;
import pl.kurs.finaltesttest.command.SignUpRequest;
import pl.kurs.finaltesttest.dto.JwtAuthenticationResponse;
import pl.kurs.finaltesttest.exception.ResourceNotFoundException;
import pl.kurs.finaltesttest.model.Patient;
import pl.kurs.finaltesttest.model.Role;
import pl.kurs.finaltesttest.model.User;
import pl.kurs.finaltesttest.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest signUpRequest) {
        Patient patient = new Patient();
        patient.setUsername(signUpRequest.getUsername());
        patient.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        patient.setRole(Role.PATIENT);

        userRepository.save(patient);
        String token = jwtService.generateToken(patient);
        return JwtAuthenticationResponse.builder().token(token).build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );
        User user = userRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String token = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder().token(token).build();
    }
}
