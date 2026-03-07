package geo.track.controller;

import geo.track.domain.Arquivo;
import geo.track.domain.OrdemDeServico;
import geo.track.dto.arquivos.ArquivoResponse;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.enums.Template;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.ArquivoExceptionMessages;
import geo.track.exception.constraint.message.EnumDomains;
import geo.track.gateway.GatewayExporData;
import geo.track.mapper.ArquivoMapper;
import geo.track.mapper.OrdemDeServicoMapper;
import geo.track.repository.ArquivoRepository;
import geo.track.service.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/arquivos")
public class ArquivosController{
    private final GatewayExporData GATEWAY_EXPORT_DATA_RABBIT_MQ;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final ArquivoRepository ARQUIVO_REPOSITORY;

    @PostMapping("/orcamento/{idOrcamento}")
    public ResponseEntity<byte[]> post(@AuthenticationPrincipal UsuarioDetalhesDto usuario, @RequestHeader("authorization") String token, @PathVariable Integer idOrcamento) {
        Integer idUsuario = usuario.getIdOficina();
        OrdemDeServico orcamento = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrcamento, idUsuario);

        GATEWAY_EXPORT_DATA_RABBIT_MQ.solicitarArquivo(OrdemDeServicoMapper.toResponse(orcamento));

        return ResponseEntity.status(201)
                .body(null);
    }

    @GetMapping("/orcamento/{idOrcamento}")
    public ResponseEntity<ArquivoResponse> get(@AuthenticationPrincipal UsuarioDetalhesDto usuario, @RequestHeader("authorization") String token, @PathVariable Integer idOrcamento) {
        Optional<Arquivo> arquivo = ARQUIVO_REPOSITORY.findByFkOrdemServicoAndTemplate(idOrcamento, Template.ORDEM_SERVICO);

        if (arquivo.isEmpty()) {
            throw new DataNotFoundException(ArquivoExceptionMessages.ARQUIVO_NAO_ENCONTRADO_ID, EnumDomains.ARQUIVO);
        }

        return ResponseEntity.status(201)
                .body(ArquivoMapper.toResponse(arquivo.get()));
    }
}
