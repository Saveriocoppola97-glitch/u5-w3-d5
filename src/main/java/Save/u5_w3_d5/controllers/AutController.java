package Save.u5_w3_d5.controllers;

import Save.u5_w3_d5.entities.User;
import Save.u5_w3_d5.payloads.AutentificazionePayload;
import Save.u5_w3_d5.payloads.UserLoginPayload;
import Save.u5_w3_d5.payloads.UserRegistrationPayload;
import Save.u5_w3_d5.services.AutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutController {

    @Autowired
    private AutService autService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody @Validated UserRegistrationPayload body) {
        return autService.register(body);
    }

    @PostMapping("/login")
    public AutentificazionePayload login(@RequestBody @Validated UserLoginPayload body) {
        return autService.authenticate(body);
    }
}
