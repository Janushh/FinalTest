package pl.kurs.finaltesttest.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends User {
    private String name;
    private String surname;
    private int age;
    private String pesel;
    private boolean isLocked;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    public void setAppointments(List<Appointment> appointments) {
        if (appointments == null) {
            this.appointments = new ArrayList<>();
        } else {
            this.appointments = appointments;
        }
    }
}
