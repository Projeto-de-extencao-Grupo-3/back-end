package geo.track.service;

import geo.track.config.rabbitMQ.RabbitMQConfig;
import geo.track.domain.Arquivo;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.enums.Formato;
import geo.track.enums.StatusArquivo;
import geo.track.enums.Template;
import geo.track.exception.AcceptedException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.ServiceUnavailableException;
import geo.track.exception.constraint.message.ArquivoExceptionMessages;
import geo.track.exception.constraint.message.Domains;
import geo.track.gateway.GatewayExporData;
import geo.track.log.Log;
import geo.track.mapper.OrdemDeServicoMapper;
import geo.track.repository.ArquivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ArquivoService {
    private final ArquivoRepository ARQUIVO_REPOSITORY;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final GatewayExporData GATEWAY_EXPORT_DATA;
    private final HashMap<Template, String> routingKeyMap = new HashMap<>();
    private final Log log;

    {
        routingKeyMap.put(Template.ORCAMENTO, RabbitMQConfig.ROUTING_KEY_ORCAMENTO);
        routingKeyMap.put(Template.ORDEM_SERVICO, RabbitMQConfig.ROUTING_KEY_ORDEM_SERVICO);
    }

    @Transactional
    public void solicitarGeracao(Integer idOrdem, Integer idOficina, Template template) {
        log.info("Iniciando solicitação de geração de arquivo de Ordem de Serviço. Ordem ID: {}, Oficina ID: {}", idOrdem, idOficina);
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdem, idOficina);

        String routingKey = routingKeyMap.get(template);

        this.garantirRegistroArquivo(ordem.getIdOrdemServico(), template);

        boolean sucesso = GATEWAY_EXPORT_DATA.solicitarArquivo(OrdemDeServicoMapper.toResponse(ordem), routingKey);

        if (!sucesso) {
            log.error("Falha ao enviar solicitação para a fila do RabbitMQ. Routing Key: {}", routingKey);
            throw new ServiceUnavailableException(Domains.ARQUIVO, ArquivoExceptionMessages.INDISPONIBILIDADE_SERVICO);
        }
        log.info("Solicitação de geração de arquivo de Ordem de Serviço enviada com sucesso.");
    }

    public Arquivo buscarArquivo(Integer idOrdem, Template template) {
        log.info("Buscando status/arquivo do {} para a Ordem ID: {}", template, idOrdem);
        Arquivo arquivo = ARQUIVO_REPOSITORY.findByFkOrdemServicoAndTemplate(idOrdem, template)
                .orElseThrow(() -> new DataNotFoundException(ArquivoExceptionMessages.ARQUIVO_NAO_ENCONTRADO_ID, Domains.ARQUIVO));

        validarStatusConcluido(arquivo);
        return arquivo;
    }

    private void garantirRegistroArquivo(Integer idOrdemServico, Template template) {
        if (!ARQUIVO_REPOSITORY.existsByFkOrdemServicoAndTemplate(idOrdemServico, template)) {
            log.info("Registrando novo arquivo no banco com status A_FAZER. Ordem ID: {}, Template: {}", idOrdemServico, template);
            ARQUIVO_REPOSITORY.save(Arquivo.builder()
                    .formato(Formato.PDF)
                    .fkOrdemServico(idOrdemServico)
                    .template(template)
                    .status(StatusArquivo.A_FAZER)
                    .build());
        }
    }

    private void validarStatusConcluido(Arquivo arquivo) {
        if (!StatusArquivo.CONCLUIDO.equals(arquivo.getStatus())) {
            log.warn("Tentativa de acessar arquivo que ainda não está concluído. Arquivo ID: {}, Status: {}", arquivo.getIdArquivo(), arquivo.getStatus());
            throw new AcceptedException(ArquivoExceptionMessages.ARQUIVO_NAO_CONCLUIDO, Domains.ARQUIVO);
        }
    }
}
