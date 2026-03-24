package geo.track.infraestructure.config;

import geo.track.infraestructure.log.Log;
import geo.track.infraestructure.log.LogImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public Log log() {
        return new LogImplementation();
    }

}
