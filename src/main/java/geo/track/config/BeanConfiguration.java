package geo.track.config;

import geo.track.log.Log;
import geo.track.log.LogImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public Log log() {
        return new LogImplementation();
    }

}
