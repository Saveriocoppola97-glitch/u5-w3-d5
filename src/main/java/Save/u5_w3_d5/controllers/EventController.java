package Save.u5_w3_d5.controllers;

import Save.u5_w3_d5.entities.Event;
import Save.u5_w3_d5.entities.User;
import Save.u5_w3_d5.payloads.EventPayload;
import Save.u5_w3_d5.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ORGANIZZATORE')")
    public Event createEvent(@RequestBody @Validated EventPayload body, @AuthenticationPrincipal User currentOrganizer) {
        return eventService.save(body, currentOrganizer);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ORGANIZZATORE')")
    public Event updateEvent(@PathVariable Long id, @RequestBody @Validated EventPayload body, @AuthenticationPrincipal User currentOrganizer) {
        return eventService.update(id, body, currentOrganizer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ORGANIZZATORE')")
    public void deleteEvent(@PathVariable Long id, @AuthenticationPrincipal User currentOrganizer) {
        eventService.delete(id, currentOrganizer);
    }
}
