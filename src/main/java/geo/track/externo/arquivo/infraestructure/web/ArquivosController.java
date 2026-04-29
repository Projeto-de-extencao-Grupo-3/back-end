package geo.track.externo.arquivo.infraestructure.web;

import geo.track.externo.arquivo.infraestructure.persistence.entity.Arquivo;
import geo.track.externo.arquivo.infraestructure.persistence.entity.Categoria;
import geo.track.externo.arquivo.infraestructure.response.ArquivoResponse;
import geo.track.infraestructure.auth.model.UsuarioDetalhesDto;
import geo.track.externo.arquivo.infraestructure.ArquivoMapper;
import geo.track.externo.arquivo.domain.ArquivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @GetMapping("/relatorio/mensal")
    public ResponseEntity<ArquivoResponse> getRelatorioMensal(
            @AuthenticationPrincipal UsuarioDetalhesDto usuario,
            @RequestParam Integer mesReferencia,
            @RequestParam Integer anoReferencia
    ) {
        var arquivo = ARQUIVO_SERVICE.buscarArquivoRelatorioMensal(usuario.getIdOficina(), mesReferencia, anoReferencia);
        return ResponseEntity.status(200).body(ArquivoMapper.toResponse(arquivo));
    }

    @PostMapping("/vistoria/{fkOrdemServico}/{categoria}")
    public ResponseEntity<ArquivoResponse> postArmazenarVistoriasS3Bucket(@PathVariable Integer fkOrdemServico, @RequestParam("file") MultipartFile file, @PathVariable Categoria categoria){
        Arquivo arquivo = ARQUIVO_SERVICE.armazenarArquivoDeVistoria(fkOrdemServico, file, Categoria.ORDEM_SERVICO);

        ArquivoResponse response = new ArquivoResponse(
                arquivo.getIdArquivo(),
                arquivo.getNome(),
                arquivo.getCategoria().name(),
                arquivo.getFormato().name(),
                arquivo.getUrl(),
                arquivo.getDataCriacao().toString(),
                arquivo.getDataAtualizacao() != null ? arquivo.getDataAtualizacao().toString() : null
        );

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/vistoria/{fkOrdemServico}")
    public ResponseEntity<List<ArquivoResponse>> listarVistoriasPorOS(@PathVariable Integer fkOrdemServico){
        List<Arquivo> arquivos = ARQUIVO_SERVICE.listarArquivosOS(fkOrdemServico);

        List<ArquivoResponse> response = arquivos.stream()
                .map(arquivo -> new ArquivoResponse(
                        arquivo.getIdArquivo(),
                        arquivo.getNome(),
                        arquivo.getCategoria().name(),
                        arquivo.getFormato().name(),
                        arquivo.getUrl(),
                        arquivo.getDataCriacao().toString(),
                        arquivo.getDataAtualizacao() != null ? arquivo.getDataAtualizacao().toString() : null
                ))
                .toList();

        return ResponseEntity.status(200).body(response);
    }
}
