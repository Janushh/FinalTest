package pl.kurs.finaltesttest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.finaltesttest.model.Appointment;
import pl.kurs.finaltesttest.model.Doctor;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
