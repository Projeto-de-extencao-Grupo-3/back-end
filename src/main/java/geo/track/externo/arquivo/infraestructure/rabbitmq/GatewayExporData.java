package geo.track.externo.arquivo.infraestructure.rabbitmq;

import geo.track.infraestructure.config.rabbitMQ.RabbitMQConfig;
import geo.track.jornada.infraestructure.response.listagem.OrdemDeServicoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatewayExporData {
    private final RabbitTemplate rabbitTemplate;

    public <T> Boolean solicitarArquivo (T dadosArquivo, String routingKey, Integer mesReferencia, Integer anoReferencia) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_PROJETO, routingKey, dadosArquivo, message -> {
                MessageProperties props = message.getMessageProperties();
                props.setHeader("mesReferencia", mesReferencia);
                props.setHeader("anoReferencia", anoReferencia);
                return message;
            });
        } catch (Exception e) {
            return false;
        }

        return true;
    }


    public <T> Boolean solicitarArquivo (T dadosArquivo, String routingKey) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_PROJETO, routingKey, dadosArquivo, message -> {
                MessageProperties props = message.getMessageProperties();
                return message;
            });
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
