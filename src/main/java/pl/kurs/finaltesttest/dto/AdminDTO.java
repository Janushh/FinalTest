package pl.kurs.finaltesttest.dto;

import lombok.Getter;
import lombok.Setter;
import pl.kurs.finaltesttest.model.AdminStatus;

@Getter
@Setter
public class AdminDTO {
    private Long id;
    private String name;
    private String surname;
    private String pesel;
    private String login;
    private AdminStatus status;
}
