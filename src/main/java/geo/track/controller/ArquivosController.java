package geo.track.controller;

import geo.track.dto.arquivos.ArquivoResponse;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.enums.Template;
import geo.track.mapper.ArquivoMapper;
import geo.track.service.ArquivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/arquivos")
public class ArquivosController {
    private final ArquivoService ARQUIVO_SERVICE;

    @PostMapping("/ordem_servico/{idOrcamento}")
    public ResponseEntity<Void> postOrdemServico(@AuthenticationPrincipal UsuarioDetalhesDto usuario, @PathVariable Integer idOrcamento) {
        ARQUIVO_SERVICE.solicitarGeracao(idOrcamento, usuario.getIdOficina(), Template.ORCAMENTO);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/orcamento/{idOrdemServico}")
    public ResponseEntity<Void> postOrcamento(@AuthenticationPrincipal UsuarioDetalhesDto usuario, @PathVariable Integer idOrdemServico) {
        ARQUIVO_SERVICE.solicitarGeracao(idOrdemServico, usuario.getIdOficina(), Template.ORDEM_SERVICO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/orcamento/{idOrcamento}")
    public ResponseEntity<ArquivoResponse> getOrcamento(@PathVariable Integer idOrcamento) {
        var arquivo = ARQUIVO_SERVICE.buscarArquivo(idOrcamento, Template.ORCAMENTO);
        return ResponseEntity.status(200).body(ArquivoMapper.toResponse(arquivo));
    }

    @GetMapping("/ordem_servico/{idOrdemServico}")
    public ResponseEntity<ArquivoResponse> getOrdemServico(@PathVariable Integer idOrdemServico) {
        var arquivo = ARQUIVO_SERVICE.buscarArquivo(idOrdemServico, Template.ORDEM_SERVICO);
        return ResponseEntity.status(200).body(ArquivoMapper.toResponse(arquivo));
    }
}
