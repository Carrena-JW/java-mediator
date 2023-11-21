package carrena.mediator.config;


import carrena.mediator.mediator.Mediator;
import carrena.mediator.mediator.MediatorImpl;
import carrena.mediator.mediator.RequestCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public Mediator mediator(){
        return new MediatorImpl();
    }
}
