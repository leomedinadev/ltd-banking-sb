package ec.com.ltd.banking.sb.application.dto;

public record TransactionDTO(
    String id,
    String type,
    double amount
) {

}
