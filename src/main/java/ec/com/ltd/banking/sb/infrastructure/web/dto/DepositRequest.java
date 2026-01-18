package ec.com.ltd.banking.sb.infrastructure.web.dto;

import jakarta.validation.constraints.Positive;

public record DepositRequest(@Positive double amount) {

}
