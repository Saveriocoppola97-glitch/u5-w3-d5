package Save.u5_w3_d5.repositories;

import Save.u5_w3_d5.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
