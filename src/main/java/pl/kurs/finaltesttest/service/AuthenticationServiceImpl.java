package pl.kurs.finaltesttest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kurs.finaltesttest.command.SignInRequest;
import pl.kurs.finaltesttest.command.SignUpRequest;
import pl.kurs.finaltesttest.dto.JwtAuthenticationResponse;
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
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(token).build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        User user = userRepository.findByUsername(signInRequest.getUsername());
        String token = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(token).build();
    }
}
