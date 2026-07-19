package Save.u5_w3_d5.services;

import Save.u5_w3_d5.entities.Event;
import Save.u5_w3_d5.entities.User;
import Save.u5_w3_d5.entities.Ruoli;
import Save.u5_w3_d5.exceptions.NotFoundException;
import Save.u5_w3_d5.exceptions.UnauthorizedException;
import Save.u5_w3_d5.payloads.EventPayload;
import Save.u5_w3_d5.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento " + id + " non trovato."));
    }

    public Event save(EventPayload payload, User organizer) {
        if (!organizer.getRole().equals(Ruoli.ROLE_ORGANIZZATORE)) {
            throw new UnauthorizedException("Non sei un organizzatore d'eventi.");
        }

        Event event = new Event();
        event.setTitle(payload.title());
        event.setDescription(payload.description());
        event.setDate(payload.date());
        event.setLocation(payload.location());
        event.setMaxSeats(payload.maxSeats());
        event.setOrganizer(organizer);

        return eventRepository.save(event);
    }

    public Event update(Long id, EventPayload payload, User organizer) {
        Event event = findById(id);

        if (!event.getOrganizer().getId().equals(organizer.getId())) {
            throw new UnauthorizedException("Non sei l'organizzatore del evento.");
        }

        event.setTitle(payload.title());
        event.setDescription(payload.description());
        event.setDate(payload.date());
        event.setLocation(payload.location());
        event.setMaxSeats(payload.maxSeats());

        return eventRepository.save(event);
    }

    public void delete(Long id, User organizer) {
        Event event = findById(id);

        if (!event.getOrganizer().getId().equals(organizer.getId())) {
            throw new UnauthorizedException("Non sei l'organizzatore del evento.");
        }

        eventRepository.delete(event);
    }
}
