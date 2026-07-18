package Save.u5_w3_d5.services;

import Save.u5_w3_d5.entities.User;
import Save.u5_w3_d5.exceptions.NotFoundException;
import Save.u5_w3_d5.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente" + id + " non trovato."));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Utente" + username + " non trovato."));
    }
}
