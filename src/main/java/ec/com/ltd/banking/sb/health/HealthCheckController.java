package ec.com.ltd.banking.sb.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

  @GetMapping("/health")
  public String healthCheck() {
    return "UP";
  }
}
