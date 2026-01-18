package ec.com.ltd.banking.sb.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateAccountRequest(
    @NotBlank String customerId,
    @Positive double initialBalance
) {

}
