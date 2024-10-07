package pl.kurs.finaltesttest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO {
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String pesel;

}
