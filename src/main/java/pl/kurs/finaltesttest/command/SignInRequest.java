package pl.kurs.finaltesttest.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    private String username;
    private String password;
}
