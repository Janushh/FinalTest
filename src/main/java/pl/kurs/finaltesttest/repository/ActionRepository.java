package pl.kurs.finaltesttest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.finaltesttest.model.Action;

public interface ActionRepository extends JpaRepository<Action, Long> {
}
