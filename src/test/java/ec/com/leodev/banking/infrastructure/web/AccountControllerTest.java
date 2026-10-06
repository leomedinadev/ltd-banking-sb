package ec.com.leodev.banking.infrastructure.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void should_withdraw_and_return_updated_balance() throws Exception {
    //arrange
    String id = createAccount("100.00");

    //act + assert
    mockMvc.perform(post("/api/accounts/{id}/withdraw", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"amount\": 30.25}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.balance").value(69.75))
        .andExpect(jsonPath("$.transactions[0].type").value("WITHDRAWAL"));

    mockMvc.perform(get("/api/accounts/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.balance").value(69.75));
  }

  @Test
  void should_return_400_when_insufficient_balance() throws Exception {
    String id = createAccount("100.00");

    mockMvc.perform(post("/api/accounts/{id}/withdraw", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"amount\": 150}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_return_400_when_amount_is_invalid() throws Exception {
    String id = createAccount("100.00");

    // negativo, con más de 2 decimales y ausente
    for (String body : new String[]{"{\"amount\": -1}", "{\"amount\": 1.005}", "{}"}) {
      mockMvc.perform(post("/api/accounts/{id}/deposit", id)
              .contentType(MediaType.APPLICATION_JSON)
              .content(body))
          .andExpect(status().isBadRequest());
    }
  }

  @Test
  void should_return_400_when_json_is_malformed() throws Exception {
    mockMvc.perform(post("/api/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"customerId\": "))
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_return_404_when_account_or_route_does_not_exist() throws Exception {
    mockMvc.perform(get("/api/accounts/{id}", "no-existe"))
        .andExpect(status().isNotFound());
    mockMvc.perform(get("/api/no-existe"))
        .andExpect(status().isNotFound());
  }

  private String createAccount(String initialBalance) throws Exception {
    String response = mockMvc.perform(post("/api/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"customerId\": \"123\", \"initialBalance\": " + initialBalance + "}"))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
    return JsonPath.read(response, "$.id");
  }
}
