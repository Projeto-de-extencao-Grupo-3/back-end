package geo.track.config.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_GERAR_ARQUIVO_ORDEM_SERVICO = "export.data.gerar.arquivo";
    public static final String QUEUE_GERAR_ARQUIVO_ORCAMENTO = "export.data.gerar.arquivo.orcamento";
    public static final String EXCHANGE_PROJETO = "export.data.exchange";

    // Deixei público (opcional) caso queira usar essas constantes no seu Producer/Publisher
    public static final String ROUTING_KEY_ORDEM_SERVICO = "export.data.routing.key";
    public static final String ROUTING_KEY_ORCAMENTO = "export.data.routing.key.orcamento";

    @Bean
    public Queue queue_a() {
        return new Queue(QUEUE_GERAR_ARQUIVO_ORDEM_SERVICO, true); // true = durável
    }

    @Bean
    public Queue queue_b() {
        return new Queue(QUEUE_GERAR_ARQUIVO_ORCAMENTO, true); // true = durável
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_PROJETO);
    }

    // Uso do @Qualifier para indicar exatamente qual bean de fila usar
    @Bean
    public Binding bindingA(@Qualifier("queue_a") Queue fila, DirectExchange exchange) {
        return BindingBuilder.bind(fila).to(exchange).with(ROUTING_KEY_ORDEM_SERVICO);
    }

    // Uso do @Qualifier para a segunda fila
    @Bean
    public Binding bindingB(@Qualifier("queue_b") Queue fila, DirectExchange exchange) {
        return BindingBuilder.bind(fila).to(exchange).with(ROUTING_KEY_ORCAMENTO);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(); // Para enviar e receber como JSON
    }
}