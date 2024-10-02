package pl.kurs.finaltesttest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.finaltesttest.model.Doctor;
import pl.kurs.finaltesttest.model.Patient;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
