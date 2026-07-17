package Save.u5_w3_d5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorsHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadRequest(BadRequestException ex) {
        Map<String, Object> infoErrore = new HashMap<>();
        infoErrore.put("dettaglio", ex.getMessage());
        infoErrore.put("orario", LocalDateTime.now());
        return infoErrore;
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handleUnauthorized(UnauthorizedException ex) {
        Map<String, Object> infoErrore = new HashMap<>();
        infoErrore.put("dettaglio", ex.getMessage());
        infoErrore.put("orario", LocalDateTime.now());
        return infoErrore;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(NotFoundException ex) {
        Map<String, Object> infoErrore = new HashMap<>();
        infoErrore.put("dettaglio", ex.getMessage());
        infoErrore.put("orario", LocalDateTime.now());
        return infoErrore;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> infoErrore = new HashMap<>();
        Map<String, String> mappaCampi = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                mappaCampi.put(error.getField(), error.getDefaultMessage())
        );

        infoErrore.put("dettaglio", "Errori nei dati.");
        infoErrore.put("campiSbagliati", mappaCampi);
        infoErrore.put("orario", LocalDateTime.now());
        return infoErrore;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleGenericException(Exception ex) {
        Map<String, Object> infoErrore = new HashMap<>();
        infoErrore.put("dettaglio", "Server non funzionante.");
        infoErrore.put("orario", LocalDateTime.now());
        return infoErrore;
    }
}
