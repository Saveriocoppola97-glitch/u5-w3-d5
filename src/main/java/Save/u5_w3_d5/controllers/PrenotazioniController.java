package Save.u5_w3_d5.controllers;

import Save.u5_w3_d5.entities.Prenotazioni;
import Save.u5_w3_d5.entities.User;
import Save.u5_w3_d5.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class PrenotazioniController {

    @Autowired
    private PrenotazioniService prenotazioniService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ORGANIZZATORE')")
    public List<Prenotazioni> getAllReservations() {
        return prenotazioniService.findAll();
    }

    @PostMapping("/event/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazioni createReservation(@PathVariable Long eventId, @AuthenticationPrincipal User currentUser) {
        return prenotazioniService.createReservation(eventId, currentUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelReservation(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        prenotazioniService.cancelReservation(id, currentUser);
    }
}
