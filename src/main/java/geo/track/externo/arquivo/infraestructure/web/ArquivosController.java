package geo.track.externo.arquivo.infraestructure.web;

import geo.track.externo.arquivo.infraestructure.persistence.entity.Categoria;
import geo.track.externo.arquivo.infraestructure.response.ArquivoResponse;
import geo.track.infraestructure.auth.model.UsuarioDetalhesDto;
import geo.track.externo.arquivo.infraestructure.ArquivoMapper;
import geo.track.externo.arquivo.domain.ArquivoService;
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
        ARQUIVO_SERVICE.solicitarGeracao(idOrcamento, usuario.getIdOficina(), Categoria.ORDEM_SERVICO);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/orcamento/{idOrdemServico}")
    public ResponseEntity<Void> postOrcamento(@AuthenticationPrincipal UsuarioDetalhesDto usuario, @PathVariable Integer idOrdemServico) {
        ARQUIVO_SERVICE.solicitarGeracao(idOrdemServico, usuario.getIdOficina(), Categoria.ORCAMENTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/orcamento/{idOrcamento}")
    public ResponseEntity<ArquivoResponse> getOrcamento(@PathVariable Integer idOrcamento) {
        var arquivo = ARQUIVO_SERVICE.buscarArquivo(idOrcamento, Categoria.ORCAMENTO);
        return ResponseEntity.status(200).body(ArquivoMapper.toResponse(arquivo));
    }

    @GetMapping("/ordem_servico/{idOrdemServico}")
    public ResponseEntity<ArquivoResponse> getOrdemServico(@PathVariable Integer idOrdemServico) {
        var arquivo = ARQUIVO_SERVICE.buscarArquivo(idOrdemServico, Categoria.ORDEM_SERVICO);
        return ResponseEntity.status(200).body(ArquivoMapper.toResponse(arquivo));
    }

    @PostMapping("/relatorio/mensal")
    public ResponseEntity<Void> postRelatorioMensal(
            @AuthenticationPrincipal UsuarioDetalhesDto usuario,
            @RequestParam Integer mesReferencia,
            @RequestParam Integer anoReferencia) {
        ARQUIVO_SERVICE.solicitarGeracaoRelatorioMensal(usuario.getIdOficina(), mesReferencia, anoReferencia);
        return ResponseEntity.status(201).build();
    }
}
