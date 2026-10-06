package ec.com.leodev.banking.infrastructure.web.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreateAccountRequest(
    @NotBlank String customerId,
    @NotNull @Positive @Digits(integer = 17, fraction = 2) BigDecimal initialBalance
) {

}
