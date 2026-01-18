package ec.com.ltd.banking.sb.infrastructure.web.dto;

public record TransactionSummary(
    String id,
    String type,
    double amount
) {

}
