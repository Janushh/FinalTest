package pl.kurs.finaltesttest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.finaltesttest.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByDoctor_IdAndDate(Long doctorId, LocalDateTime date);  // Dodajemy filtrację na podstawie daty

    List<Appointment> findByDoctor_Id(Long doctorId);
}

