package geo.track.externo.arquivo.domain;

import geo.track.externo.arquivo.domain.model.RelatorioOrdemDeServico;
import geo.track.externo.arquivo.infraestructure.persistence.entity.Categoria;
import geo.track.externo.arquivo.infraestructure.rabbitmq.GatewayExporData;
import geo.track.infraestructure.config.rabbitMQ.RabbitMQConfig;
import geo.track.externo.arquivo.infraestructure.persistence.entity.Arquivo;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
    import geo.track.externo.arquivo.infraestructure.persistence.entity.Formato;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.ServiceUnavailableException;
import geo.track.infraestructure.exception.constraint.message.ArquivoExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.infraestructure.mapper.OrdemDeServicoMapper;
import geo.track.externo.arquivo.infraestructure.persistence.ArquivoRepository;
import geo.track.jornada.domain.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArquivoService {
    private final ArquivoRepository ARQUIVO_REPOSITORY;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final GatewayExporData GATEWAY_EXPORT_DATA;
    private final HashMap<Categoria, String> routingKeyMap = new HashMap<>();
    private final Log log;

    {
        routingKeyMap.put(Categoria.ORCAMENTO, RabbitMQConfig.ROUTING_KEY_ORCAMENTO);
        routingKeyMap.put(Categoria.ORDEM_SERVICO, RabbitMQConfig.ROUTING_KEY_ORDEM_SERVICO);
    }

    @Transactional
    public void solicitarGeracao(Integer idOrdem, Integer idOficina, Categoria categoria) {
        log.info("Iniciando solicitação de geração de arquivo de Ordem de Serviço. Ordem ID: {}, Oficina ID: {}", idOrdem, idOficina);
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdem);

        String routingKey = routingKeyMap.get(categoria);

        boolean sucesso = GATEWAY_EXPORT_DATA.solicitarArquivo(OrdemDeServicoMapper.toResponse(ordem), routingKey, idOficina);

        if (!sucesso) {
            log.error("Falha ao enviar solicitação para a fila do RabbitMQ. Routing Key: {}", routingKey);
            throw new ServiceUnavailableException(Domains.ARQUIVO, ArquivoExceptionMessages.INDISPONIBILIDADE_SERVICO);
        }
        log.info("Solicitação de geração de arquivo de Ordem de Serviço enviada com sucesso.");
    }

    public Arquivo buscarArquivo (Integer idOrdem, Categoria categoria) {
        log.info("Buscando status/arquivo do {} para a Ordem ID: {}", categoria, idOrdem);
//        Arquivo arquivo = ARQUIVO_REPOSITORY.findByFkOrdemServicoAndCategoria(idOrdem, categoria)
//                .orElseThrow(() -> new DataNotFoundException(ArquivoExceptionMessages.ARQUIVO_NAO_ENCONTRADO_ID, Domains.ARQUIVO));

//        return arquivo;
        return null;
    }

    public void solicitarGeracaoRelatorioMensal(Integer idOficina, Integer mesReferencia, Integer anoReferencia) {
        LocalDate dataReferencia = LocalDate.of(anoReferencia, mesReferencia, 1);
        log.info("Iniciando solicitação de geração de relatório mensal. Oficina ID: {}, Mês Referência: {}, Ano Referência: {}", idOficina, mesReferencia, anoReferencia);

        List<OrdemDeServico> ordens = ORDEM_SERVICO_SERVICE.listarOrdensServicoIntervaloMeses(dataReferencia);

        RelatorioOrdemDeServico relatorioModel = RelatorioOrdemDeServico.build(ordens, mesReferencia, anoReferencia);

        boolean sucesso = GATEWAY_EXPORT_DATA.solicitarArquivo(relatorioModel, RabbitMQConfig.ROUTING_KEY_RELATORIO, idOficina);

        if (!sucesso) {
            log.error("Falha ao enviar solicitação para a fila do RabbitMQ. Routing Key: {}", RabbitMQConfig.ROUTING_KEY_RELATORIO);
            throw new ServiceUnavailableException(Domains.ARQUIVO, ArquivoExceptionMessages.INDISPONIBILIDADE_SERVICO);
        }
        log.info("Solicitação de geração de arquivo de Ordem de Serviço enviada com sucesso.");
    }
}
