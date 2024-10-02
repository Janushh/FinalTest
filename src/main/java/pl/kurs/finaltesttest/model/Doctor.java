package pl.kurs.finaltesttest.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String specialization;
    private String pesel;
    private int age;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
}
