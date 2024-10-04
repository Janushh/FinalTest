package pl.kurs.finaltesttest.service;

import pl.kurs.finaltesttest.command.SignInRequest;
import pl.kurs.finaltesttest.command.SignUpRequest;
import pl.kurs.finaltesttest.dto.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
}
