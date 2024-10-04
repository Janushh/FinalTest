package pl.kurs.finaltesttest.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@DiscriminatorValue("DOCTOR")
public class Doctor extends User {
    private String name;
    private String surname;
    private String specialization;
    private String pesel;
    private int age;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;
}