package pl.kurs.finaltesttest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.finaltesttest.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
