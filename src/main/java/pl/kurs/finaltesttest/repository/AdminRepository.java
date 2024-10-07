package pl.kurs.finaltesttest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.finaltesttest.model.Admin;
import pl.kurs.finaltesttest.model.AdminStatus;

import java.util.List;
import java.util.Optional;


public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByName(String name);

    Optional<Admin> findByUsername(String username);
}
