package Save.u5_w3_d5.payloads;

import jakarta.validation.constraints.NotBlank;

public record UserLoginPayload(
        @NotBlank String username,
        @NotBlank String password
) {}