package pl.kurs.finaltesttest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDTO {
    private Long id;
    private String name;
    private String surname;
    private String specialization;
    private String pesel;
    private int age;
}
