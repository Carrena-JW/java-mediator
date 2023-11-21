package carrena.mediator;

import carrena.mediator.command.Some1Command;
import carrena.mediator.mediator.Mediator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class MediatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediatorApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(Mediator mediator){
		return args -> {
			Some1Command command = new Some1Command("adf",123L);
			System.out.println(mediator.send(command));
		};
	}
}
