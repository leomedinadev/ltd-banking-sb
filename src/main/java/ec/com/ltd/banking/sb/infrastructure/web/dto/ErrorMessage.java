package ec.com.ltd.banking.sb.infrastructure.web.dto;

import java.time.Instant;
import java.util.List;

public record ErrorMessage(
    int status,
    String error,
    String message,
    String path,
    Instant timestamp,
    List<String> details
) {

}
