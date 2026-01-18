package ec.com.ltd.banking.sb.infrastructure.web.controller;

import ec.com.ltd.banking.sb.application.dto.AccountDetailsDTO;
import ec.com.ltd.banking.sb.application.dto.CreateAccountCommand;
import ec.com.ltd.banking.sb.application.dto.DepositMoneyCommand;
import ec.com.ltd.banking.sb.application.dto.WithdrawMoneyCommand;
import ec.com.ltd.banking.sb.application.port.ICreateAccountUseCase;
import ec.com.ltd.banking.sb.application.port.IDepositMoneyUseCase;
import ec.com.ltd.banking.sb.application.port.IGetAccountDetailsUseCase;
import ec.com.ltd.banking.sb.application.port.IWithdrawMoneyUseCase;
import ec.com.ltd.banking.sb.infrastructure.web.dto.AccountResponse;
import ec.com.ltd.banking.sb.infrastructure.web.dto.CreateAccountRequest;
import ec.com.ltd.banking.sb.infrastructure.web.dto.DepositRequest;
import ec.com.ltd.banking.sb.infrastructure.web.dto.WithdrawRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountController {

  private final ICreateAccountUseCase createAccountUseCase;
  private final IWithdrawMoneyUseCase withdrawMoneyUseCase;
  private final IDepositMoneyUseCase depositMoneyUseCase;
  private final IGetAccountDetailsUseCase getAccountDetailsUseCase;

  @PostMapping
  public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
    var command = new CreateAccountCommand(request.customerId(), request.initialBalance());
    var dto = createAccountUseCase.create(command);
    return ResponseEntity.status(HttpStatus.CREATED).body(AccountResponse.from(dto));
  }

  @PostMapping("/{id}/deposit")
  public ResponseEntity<AccountResponse> deposit(@PathVariable String id, @Valid @RequestBody
      DepositRequest request) {
    var command = new DepositMoneyCommand(id, request.amount());
    var dto = depositMoneyUseCase.deposit(command);
    return ResponseEntity.ok(AccountResponse.from(dto));
  }

  @PostMapping("/{id}/withdraw")
  public ResponseEntity<AccountResponse> withdraw(@PathVariable String id, @Valid @RequestBody
      WithdrawRequest request) {
    var command = new WithdrawMoneyCommand(id, request.amount());
    var dto = withdrawMoneyUseCase.withdraw(command);
    return ResponseEntity.ok(AccountResponse.from(dto));
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccountResponse> findById(@PathVariable String id) {
    var dto = getAccountDetailsUseCase.getById(id);
    return ResponseEntity.ok(AccountResponse.from(dto));
  }
}
