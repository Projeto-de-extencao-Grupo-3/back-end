package geo.track.gateway;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import geo.track.config.rabbitMQ.RabbitMQConfig;
import geo.track.dto.os.response.OrdemDeServicoResponse;
import geo.track.enums.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatewayExporData {
    private final RabbitTemplate rabbitTemplate;

    public void solicitarArquivo(OrdemDeServicoResponse ordemDeServico, Template template) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_PROJETO, "export.data.routing.key", ordemDeServico, message -> {
            MessageProperties props = message.getMessageProperties();
            props.setHeader("x-template-arquivo", template.toString());

            return message;
        });
    }
}
