package pl.kurs.finaltesttest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.finaltesttest.model.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
