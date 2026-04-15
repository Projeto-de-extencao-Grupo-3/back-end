package geo.track.infraestructure.config.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_GERAR_ARQUIVO_ORDEM_SERVICO = "export.data.gerar.arquivo.ordem-servico";
    public static final String QUEUE_GERAR_ARQUIVO_ORCAMENTO = "export.data.gerar.arquivo.orcamento";
    public static final String QUEUE_GERAR_ARQUIVO_RELATORIO = "export.data.gerar.arquivo.relatorio";
    public static final String EXCHANGE_PROJETO = "export.data.exchange";

    public static final String ROUTING_KEY_ORDEM_SERVICO = "export.data.routing.key";
    public static final String ROUTING_KEY_ORCAMENTO = "export.data.routing.key.orcamento";
    public static final String ROUTING_KEY_RELATORIO = "export.data.routing.key.relatorio";

    @Bean
    public Queue queue_a() {
        return QueueBuilder.durable(QUEUE_GERAR_ARQUIVO_ORDEM_SERVICO)
                .ttl(86400000)
                .maxLength(10000)
                .build();
    }

    @Bean
    public Queue queue_b() {
        return QueueBuilder.durable(QUEUE_GERAR_ARQUIVO_ORCAMENTO)
                .ttl(3600000)
                .build();
    }

    @Bean
    public Queue queue_c() {
        return QueueBuilder.durable(QUEUE_GERAR_ARQUIVO_RELATORIO)
                .build();
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_PROJETO);
    }

    @Bean
    public Binding bindingA(@Qualifier("queue_a") Queue fila, DirectExchange exchange) {
        return BindingBuilder.bind(fila).to(exchange).with(ROUTING_KEY_ORDEM_SERVICO);
    }

    @Bean
    public Binding bindingB(@Qualifier("queue_b") Queue fila, DirectExchange exchange) {
        return BindingBuilder.bind(fila).to(exchange).with(ROUTING_KEY_ORCAMENTO);
    }

    @Bean
    public Binding bindingC(@Qualifier("queue_c") Queue fila, DirectExchange exchange) {
        return BindingBuilder.bind(fila).to(exchange).with(ROUTING_KEY_RELATORIO);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}