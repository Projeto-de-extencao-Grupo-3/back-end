package geo.track.controller;

import geo.track.config.rabbitMQ.RabbitMQConfig;
import geo.track.domain.Arquivo;
import geo.track.domain.OrdemDeServico;
import geo.track.dto.arquivos.ArquivoResponse;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.enums.Formato;
import geo.track.enums.StatusArquivo;
import geo.track.enums.Template;
import geo.track.exception.AcceptedException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.ServiceUnavailableException;
import geo.track.exception.constraint.message.ArquivoExceptionMessages;
import geo.track.exception.constraint.message.Domains;
import geo.track.gateway.GatewayExporData;
import geo.track.mapper.ArquivoMapper;
import geo.track.mapper.OrdemDeServicoMapper;
import geo.track.repository.ArquivoRepository;
import geo.track.service.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/arquivos")
public class ArquivosController {
    private final GatewayExporData GATEWAY_EXPORT_DATA_RABBIT_MQ;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final ArquivoRepository ARQUIVO_REPOSITORY;

    @PostMapping("/ordem_servico/{idOrcamento}")
    public ResponseEntity<byte[]> post(@AuthenticationPrincipal UsuarioDetalhesDto usuario, @RequestHeader("authorization") String token, @PathVariable Integer idOrcamento) {
        Integer idUsuario = usuario.getIdOficina();
        OrdemDeServico orcamento = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrcamento, idUsuario);

        if (!ARQUIVO_REPOSITORY.existsByFkOrdemServicoAndTemplate(orcamento.getIdOrdemServico(), Template.ORDEM_SERVICO)) ARQUIVO_REPOSITORY.save(Arquivo.builder().formato(Formato.PDF).fkOrdemServico(orcamento.getIdOrdemServico()).template(Template.ORDEM_SERVICO).status(StatusArquivo.A_FAZER).build());
        Boolean sucess = GATEWAY_EXPORT_DATA_RABBIT_MQ.solicitarArquivo(OrdemDeServicoMapper.toResponse(orcamento), RabbitMQConfig.ROUTING_KEY_ORDEM_SERVICO);

        if (!sucess) {
            throw new ServiceUnavailableException(Domains.ARQUIVO, ArquivoExceptionMessages.INDISPONIBILIDADE_SERVICO);
        }

        return ResponseEntity.status(201)
                .body(null);
    }

    @PostMapping("/orcamento/{idOrcamento}")
    public ResponseEntity<byte[]> postC(@AuthenticationPrincipal UsuarioDetalhesDto usuario, @RequestHeader("authorization") String token, @PathVariable Integer idOrcamento) {
        Integer idUsuario = usuario.getIdOficina();
        OrdemDeServico orcamento = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrcamento, idUsuario);

        if (!ARQUIVO_REPOSITORY.existsByFkOrdemServicoAndTemplate(orcamento.getIdOrdemServico(), Template.ORCAMENTO)) ARQUIVO_REPOSITORY.save(Arquivo.builder().formato(Formato.PDF).fkOrdemServico(orcamento.getIdOrdemServico()).template(Template.ORCAMENTO).status(StatusArquivo.A_FAZER).build());
        Boolean sucess = GATEWAY_EXPORT_DATA_RABBIT_MQ.solicitarArquivo(OrdemDeServicoMapper.toResponse(orcamento), RabbitMQConfig.ROUTING_KEY_ORCAMENTO);

        if (!sucess) {
            throw new ServiceUnavailableException(Domains.ARQUIVO, ArquivoExceptionMessages.INDISPONIBILIDADE_SERVICO);
        }

        return ResponseEntity.status(201)
                .body(null);
    }

    @GetMapping("/orcamento/{idOrcamento}")
    public ResponseEntity<ArquivoResponse> get(@AuthenticationPrincipal UsuarioDetalhesDto usuario, @RequestHeader("authorization") String token, @PathVariable Integer idOrcamento) {
        Arquivo arquivo = ARQUIVO_REPOSITORY.findByFkOrdemServicoAndTemplate(idOrcamento, Template.ORCAMENTO).orElseThrow(() -> new DataNotFoundException(ArquivoExceptionMessages.ARQUIVO_NAO_ENCONTRADO_ID, Domains.ARQUIVO));

        if (!arquivo.getStatus().equals(StatusArquivo.CONCLUIDO)) {
            throw new AcceptedException(ArquivoExceptionMessages.ARQUIVO_NAO_CONCLUIDO, Domains.ARQUIVO);
        }

        return ResponseEntity.status(200)
                .body(ArquivoMapper.toResponse(arquivo));
    }

    @GetMapping("/ordem_servico/{idOrcamento}")
    public ResponseEntity<ArquivoResponse> getB(@AuthenticationPrincipal UsuarioDetalhesDto usuario, @RequestHeader("authorization") String token, @PathVariable Integer idOrcamento) {
        Arquivo arquivo = ARQUIVO_REPOSITORY.findByFkOrdemServicoAndTemplate(idOrcamento, Template.ORDEM_SERVICO).orElseThrow(() -> new DataNotFoundException(ArquivoExceptionMessages.ARQUIVO_NAO_ENCONTRADO_ID, Domains.ARQUIVO));

        if (!arquivo.getStatus().equals(StatusArquivo.CONCLUIDO)) {
            throw new AcceptedException(ArquivoExceptionMessages.ARQUIVO_NAO_CONCLUIDO, Domains.ARQUIVO);
        }

        return ResponseEntity.status(200)
                .body(ArquivoMapper.toResponse(arquivo));
    }
}
