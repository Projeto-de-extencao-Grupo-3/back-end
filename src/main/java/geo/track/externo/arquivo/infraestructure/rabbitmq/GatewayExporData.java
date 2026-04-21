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

    public <T> Boolean solicitarArquivo (T dadosArquivo, String routingKey, Integer idOficina) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_PROJETO, routingKey, dadosArquivo, message -> {
                MessageProperties props = message.getMessageProperties();
                props.setHeader("idOficina", idOficina);
                return message;
            });
        } catch (Exception e) {
            System.out.println("Erro ao enviar mensagem para RabbitMQ: " + e.getMessage());
            return false;
        }

        return true;
    }

}
