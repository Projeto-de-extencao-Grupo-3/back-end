package geo.track.externo.arquivo.domain;

import geo.track.externo.arquivo.domain.model.RelatorioOrdemDeServico;
import geo.track.externo.arquivo.infraestructure.persistence.entity.Categoria;
import geo.track.externo.arquivo.infraestructure.persistence.entity.Formato;
import geo.track.externo.arquivo.infraestructure.persistence.entity.Metadado;
import geo.track.externo.arquivo.infraestructure.rabbitmq.GatewayExporData;
import geo.track.gestao.oficina.domain.OficinaService;
import geo.track.infraestructure.config.rabbitMQ.RabbitMQConfig;
import geo.track.externo.arquivo.infraestructure.persistence.entity.Arquivo;
import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.ServiceUnavailableException;
import geo.track.infraestructure.exception.constraint.message.ArquivoExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.infraestructure.mapper.OrdemDeServicoMapper;
import geo.track.externo.arquivo.infraestructure.persistence.ArquivoRepository;
import geo.track.jornada.domain.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.LocalDateTime;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArquivoService {
    private final ArquivoRepository ARQUIVO_REPOSITORY;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final GatewayExporData GATEWAY_EXPORT_DATA;
    private final OficinaService OFICINA_SERVICE;
    private final HashMap<Categoria, String> routingKeyMap = new HashMap<>();
    private final Log log;
    private final S3Client s3Client;

    @Value("aws.bucket.name")
    private String BUCKET_NAME;

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
        Arquivo arquivo = ARQUIVO_REPOSITORY.findByFkOrdemServicoAndCategoria(idOrdem, categoria)
                .orElseThrow(() -> new DataNotFoundException(ArquivoExceptionMessages.ARQUIVO_NAO_ENCONTRADO_ID, Domains.ARQUIVO));

        return arquivo;
    }

    public void solicitarGeracaoRelatorioMensal(Integer idOficina, Integer mesReferencia, Integer anoReferencia) {
        LocalDate dataInicio = LocalDate.of(anoReferencia, mesReferencia, 1);
        LocalDate dataFim = dataInicio.with(TemporalAdjusters.lastDayOfMonth());

        log.info("Iniciando solicitação de geração de relatório mensal. Oficina ID: {}, Mês Referência: {}, Ano Referência: {}", idOficina, mesReferencia, anoReferencia);

        List<OrdemDeServico> ordens = ORDEM_SERVICO_SERVICE.listarOrdensServicoEntreDataInicioEDataFim(dataInicio, dataFim);

        if (ordens.isEmpty()) throw new BadBusinessRuleException(ArquivoExceptionMessages.FALTA_ORDENS_PARA_RELATORIO, Domains.ARQUIVO);

        RelatorioOrdemDeServico relatorioModel = RelatorioOrdemDeServico.build(ordens, mesReferencia, anoReferencia);

        boolean sucesso = GATEWAY_EXPORT_DATA.solicitarArquivo(relatorioModel, RabbitMQConfig.ROUTING_KEY_RELATORIO, idOficina);

        if (!sucesso) {
            log.error("Falha ao enviar solicitação para a fila do RabbitMQ. Routing Key: {}", RabbitMQConfig.ROUTING_KEY_RELATORIO);
            throw new ServiceUnavailableException(Domains.ARQUIVO, ArquivoExceptionMessages.INDISPONIBILIDADE_SERVICO);
        }
        log.info("Solicitação de geração de arquivo de Ordem de Serviço enviada com sucesso.");
    }

    public Arquivo buscarArquivoRelatorioMensal(Integer idOficina, Integer mesReferencia, Integer anoReferencia) {
        log.info("Buscando status/arquivo do relatório mensal para Oficina ID: {}, Mês Referência: {}, Ano Referência: {}", idOficina, mesReferencia, anoReferencia);
        Arquivo arquivo = ARQUIVO_REPOSITORY.findRelatorioByFkOficinaAndAnoMesReferencia(Categoria.RELATORIO, idOficina, String.format("%04d/%02d", anoReferencia, mesReferencia))
                .orElseThrow(() -> new DataNotFoundException(ArquivoExceptionMessages.ARQUIVO_NAO_ENCONTRADO_ID, Domains.ARQUIVO));

        return arquivo;
    }

    @Transactional
    public Arquivo armazenarArquivoDeVistoria(Integer idOrdem, MultipartFile arquivo, Categoria categoria) {
        log.info("Iniciando upload e vínculo de arquivo para a Ordem ID: {}", idOrdem);

        String nomeArquivo = UUID.randomUUID() + "_" + arquivo.getOriginalFilename();

        try {
            // 1. Upload usando o s3Client diretamente (corrigindo o erro do s3Service)
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(BUCKET_NAME)
                            .key(nomeArquivo)
                            .contentType(arquivo.getContentType())
                            .build(),
                    RequestBody.fromInputStream(arquivo.getInputStream(), arquivo.getSize()));

            String urlS3 = String.format("https://%s.s3.amazonaws.com/%s", BUCKET_NAME, nomeArquivo);

            Arquivo novoArquivo = Arquivo.builder()
                    .nome(arquivo.getOriginalFilename())
                    .categoria(categoria)
                    .formato(Formato.fromContentType(arquivo.getContentType()))
                    .url(urlS3)
                    .dataCriacao(LocalDateTime.now())
                     .oficina(OFICINA_SERVICE.buscarOficinaPorId(1))
                    .build();

            Metadado vinculoOS = Metadado.builder()
                    .chave("fkOrdemServico")
                    .valor(idOrdem.toString())
                    .arquivo(novoArquivo)
                    .build();

            novoArquivo.setMetadados(List.of(vinculoOS));

            return ARQUIVO_REPOSITORY.save(novoArquivo);

        } catch (Exception e) {
            log.error("Erro ao processar arquivo: {}", e.getMessage());
            throw new ServiceUnavailableException(Domains.ARQUIVO, "Erro no upload S3");
        }
    }

    public List<Arquivo> listarArquivosOS(Integer idOrdem) {
        return ARQUIVO_REPOSITORY.findAllByFkOrdemServico(idOrdem.toString());
    }

}
