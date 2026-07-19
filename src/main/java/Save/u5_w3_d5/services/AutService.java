package Save.u5_w3_d5.services;

import Save.u5_w3_d5.entities.Ruoli;
import Save.u5_w3_d5.entities.User;
import Save.u5_w3_d5.exceptions.BadRequestException;
import Save.u5_w3_d5.exceptions.UnauthorizedException;
import Save.u5_w3_d5.payloads.AutentificazionePayload;
import Save.u5_w3_d5.payloads.UserLoginPayload;
import Save.u5_w3_d5.payloads.UserRegistrationPayload;
import Save.u5_w3_d5.repositories.UserRepository;
import Save.u5_w3_d5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTools jwtTools;

    public User register(UserRegistrationPayload payload) {
        userRepository.findByUsername(payload.username()).ifPresent(user -> {
            throw new BadRequestException("Username già in uso.");
        });

        userRepository.findByEmail(payload.email()).ifPresent(user -> {
            throw new BadRequestException("Email già in uso.");
        });

        User user = new User();
        user.setUsername(payload.username());
        user.setEmail(payload.email());
        user.setPassword(passwordEncoder.encode(payload.password()));

        try {
            user.setRole(Ruoli.valueOf(payload.role().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Ruolo non valido. Usa ROLE_UTENTE o ROLE_ORGANIZZATORE.");
        }

        return userRepository.save(user);
    }

    public AutentificazionePayload authenticate(UserLoginPayload payload) {
        User user = userRepository.findByUsername(payload.username())
                .orElseThrow(() -> new UnauthorizedException("Credenziali non valide."));

        if (!passwordEncoder.matches(payload.password(), user.getPassword())) {
            throw new UnauthorizedException("Credenziali non valide.");
        }

        String token = jwtTools.createToken(user);
        return new AutentificazionePayload(token);
    }
}