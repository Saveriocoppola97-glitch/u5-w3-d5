package Save.u5_w3_d5.services;

import Save.u5_w3_d5.entities.Event;
import Save.u5_w3_d5.entities.Prenotazioni;
import Save.u5_w3_d5.entities.User;
import Save.u5_w3_d5.exceptions.BadRequestException;
import Save.u5_w3_d5.exceptions.NotFoundException;
import Save.u5_w3_d5.repositories.PrenotazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PrenotazioniService {

    @Autowired
    private PrenotazioniRepository prenotazioniRepository;

    @Autowired
    private EventService eventService;

    public Prenotazioni createReservation(Long eventId, User user) {
        Event event = eventService.findById(eventId);

        prenotazioniRepository.findByUserAndEvent(user, event).ifPresent(p -> {
            throw new BadRequestException("Hai già prenotato per l'evento.");
        });

        long currentReservationsCount = prenotazioniRepository.findAll().stream()
                .filter(p -> p.getEvent().getId().equals(eventId))
                .count();

        if (currentReservationsCount >= event.getMaxSeats()) {
            throw new BadRequestException("Posti esauriti.");
        }

        Prenotazioni reservation = new Prenotazioni();
        reservation.setUser(user);
        reservation.setEvent(event);

        return prenotazioniRepository.save(reservation);
    }

    public List<Prenotazioni> findAll() {
        return prenotazioniRepository.findAll();
    }

    public void cancelReservation(Long id, User user) {
        Prenotazioni reservation = prenotazioniRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prenotazione non trovata."));

        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Non puoi cancellare la prenotazione se non è creata da te.");
        }

        prenotazioniRepository.delete(reservation);
    }
}