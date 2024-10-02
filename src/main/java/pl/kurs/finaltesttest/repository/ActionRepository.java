package pl.kurs.finaltesttest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.finaltesttest.model.Action;
import pl.kurs.finaltesttest.model.Admin;

public interface ActionRepository extends JpaRepository<Action, Long> {
}
