package ec.com.ltd.banking.sb;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import java.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LtdBankingSbApplication {

	public static void main(String[] args) {
		SpringApplication.run(LtdBankingSbApplication.class, args);
	}

  @Bean @ConditionalOnMissingBean(BuildProperties.class)
  BuildProperties buildProperties() {
    return new BuildProperties(new Properties());
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info()
            .title("BANKING-TRANSACTIONS REST API Documentation")
            .description("BANKING-TRANSACTIONS REST API for services")
            .version(buildProperties().getVersion())
            .contact(new Contact()
                .name("Leo Tech Dev")
                .email("tioleodeveloper@outlook.com")
                .url("https://github.com/leomedinadev"))
            .license(new License()
                .name("Apache 2.0")
                .url("http://springdoc.org")));
  }

}
