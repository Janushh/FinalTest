package pl.kurs.finaltesttest.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private int age;
    private String pesel;
    private boolean isLocked;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
}
