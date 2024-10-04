package pl.kurs.finaltesttest.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    private String name;
    private String surname;
    private String pesel;
    private String login;
    private String password;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Action> actions;

    @Enumerated(EnumType.STRING)
    private AdminStatus status;
}
