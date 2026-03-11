package geo.track.config.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_GERAR_ARQUIVO = "export.data.gerar.arquivo";
    public static final String EXCHANGE_PROJETO = "export.data.exchange";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_GERAR_ARQUIVO, true); // true = durável
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_PROJETO);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("export.data.routing.key");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(); // Para enviar como JSON
    }
}
