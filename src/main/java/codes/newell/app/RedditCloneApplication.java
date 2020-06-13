package codes.newell.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import codes.newell.config.SwaggerConfig;

@SpringBootApplication
@EnableAsync
@EntityScan("codes.newell.model")
@ComponentScan("codes.newell")
@EnableJpaRepositories("codes.newell.repository")
@Import(SwaggerConfig.class)
public class RedditCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditCloneApplication.class, args);
	}

}
