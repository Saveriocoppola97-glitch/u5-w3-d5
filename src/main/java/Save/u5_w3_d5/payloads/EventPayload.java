package Save.u5_w3_d5.payloads;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record EventPayload(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull @Future LocalDate date,
        @NotBlank String location,
        @NotNull @Min(1) Integer maxSeats
) {}
