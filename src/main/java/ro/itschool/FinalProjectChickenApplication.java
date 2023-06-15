package ro.itschool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
public class FinalProjectChickenApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectChickenApplication.class, args);
	}

}
