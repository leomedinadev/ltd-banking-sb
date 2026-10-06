package ec.com.leodev.banking.infrastructure.web.controller;

import ec.com.leodev.banking.application.dto.CreateAccountCommand;
import ec.com.leodev.banking.application.dto.DepositMoneyCommand;
import ec.com.leodev.banking.application.dto.WithdrawMoneyCommand;
import ec.com.leodev.banking.application.port.ICreateAccountUseCase;
import ec.com.leodev.banking.application.port.IDepositMoneyUseCase;
import ec.com.leodev.banking.application.port.IGetAccountDetailsUseCase;
import ec.com.leodev.banking.application.port.IWithdrawMoneyUseCase;
import ec.com.leodev.banking.infrastructure.web.dto.AccountResponse;
import ec.com.leodev.banking.infrastructure.web.dto.CreateAccountRequest;
import ec.com.leodev.banking.infrastructure.web.dto.DepositRequest;
import ec.com.leodev.banking.infrastructure.web.dto.WithdrawRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Accounts", description = "API para procesos de cuentas")
public class AccountController {

  private final ICreateAccountUseCase createAccountUseCase;
  private final IWithdrawMoneyUseCase withdrawMoneyUseCase;
  private final IDepositMoneyUseCase depositMoneyUseCase;
  private final IGetAccountDetailsUseCase getAccountDetailsUseCase;

  @Operation(summary = "Crear cuenta")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Cuenta creada correctamente"),
      @ApiResponse(responseCode = "400", description = "MethodArgumentNotValidException"),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @PostMapping
  public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
    var command = new CreateAccountCommand(request.customerId(), request.initialBalance());
    var dto = createAccountUseCase.create(command);
    return ResponseEntity.status(HttpStatus.CREATED).body(AccountResponse.from(dto));
  }

  @Operation(summary = "Depositar dinero en cuenta")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Deposito creado correctamente"),
      @ApiResponse(responseCode = "400", description = "MethodArgumentNotValidException"),
      @ApiResponse(responseCode = "404", description = "AccountNotFoundException"),
      @ApiResponse(responseCode = "409", description = "La cuenta fue modificada por otra operación"),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @PostMapping("/{id}/deposit")
  public ResponseEntity<AccountResponse> deposit(@PathVariable String id, @Valid @RequestBody
      DepositRequest request) {
    var command = new DepositMoneyCommand(id, request.amount());
    var dto = depositMoneyUseCase.deposit(command);
    return ResponseEntity.ok(AccountResponse.from(dto));
  }

  @Operation(summary = "Retirar dinero de cuenta")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Retiro creado correctamente"),
      @ApiResponse(responseCode = "400", description = "InsufficientBalanceException"),
      @ApiResponse(responseCode = "404", description = "AccountNotFoundException"),
      @ApiResponse(responseCode = "409", description = "La cuenta fue modificada por otra operación"),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @PostMapping("/{id}/withdraw")
  public ResponseEntity<AccountResponse> withdraw(@PathVariable String id, @Valid @RequestBody
      WithdrawRequest request) {
    var command = new WithdrawMoneyCommand(id, request.amount());
    var dto = withdrawMoneyUseCase.withdraw(command);
    return ResponseEntity.ok(AccountResponse.from(dto));
  }

  @Operation(summary = "Consultar detalles de cuenta")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Obtener detalles de la cuenta"),
      @ApiResponse(responseCode = "404", description = "AccountNotFoundException"),
      @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @GetMapping("/{id}")
  public ResponseEntity<AccountResponse> findById(@PathVariable String id) {
    var dto = getAccountDetailsUseCase.getById(id);
    return ResponseEntity.ok(AccountResponse.from(dto));
  }
}
