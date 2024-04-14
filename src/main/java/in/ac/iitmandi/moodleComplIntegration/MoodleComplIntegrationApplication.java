package in.ac.iitmandi.moodleComplIntegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class MoodleComplIntegrationApplication {
	public static void main(String[] args) {
		SpringApplication.run(MoodleComplIntegrationApplication.class, args);
	}
}
