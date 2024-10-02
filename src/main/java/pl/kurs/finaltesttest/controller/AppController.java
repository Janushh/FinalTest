package pl.kurs.finaltesttest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kurs.finaltesttest.command.SignInRequest;
import pl.kurs.finaltesttest.command.SignUpRequest;
import pl.kurs.finaltesttest.dto.JwtAuthenticationResponse;
import pl.kurs.finaltesttest.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AppController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }
}
