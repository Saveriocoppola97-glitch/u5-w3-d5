package Save.u5_w3_d5.repositories;

import Save.u5_w3_d5.entities.Prenotazioni;
import Save.u5_w3_d5.entities.Event;
import Save.u5_w3_d5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PrenotazioniRepository extends JpaRepository<Prenotazioni, Long> {
    Optional<Prenotazioni> findByUserAndEvent(User user, Event event);
}