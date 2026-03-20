package geo.track.controller;

import geo.track.jornada.entity.OrdemDeServico;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.dto.os.response.TelaOrdemServicoResponse;
import geo.track.dto.painelControle.response.ResponsePainelControle;
import geo.track.enums.os.StatusVeiculo;
import geo.track.mapper.OrdemDeServicoMapper;
import geo.track.mapper.PainelControleMapper;
import geo.track.service.OrdemDeServicoService;
import geo.track.service.PainelControleService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/painel-controle")
@RequiredArgsConstructor
public class PainelControleController {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final PainelControleService PAINEL_CONTROLE_SERVICE;

    @GetMapping("/{idOrdem}")
    public ResponseEntity<TelaOrdemServicoResponse> findServicosProdutos(@Parameter(hidden = true) @AuthenticationPrincipal UsuarioDetalhesDto usuario, @PathVariable Integer idOrdem) {
        Integer idOficina = usuario.getIdOficina();
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdem);

        if (ordem.getServicos().isEmpty() && ordem.getProdutos().isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toTelaOrdemServicoResponse(ordem));
    }

    @GetMapping("/resumo/{idOrdem}")
    public ResponseEntity<TelaOrdemServicoResponse> resumoOrdemServico(@Parameter(hidden = true) @AuthenticationPrincipal UsuarioDetalhesDto usuario, @PathVariable Integer idOrdem) {
        Integer idOficina = usuario.getIdOficina();
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdem);

        if (ordem.getServicos().isEmpty() && ordem.getProdutos().isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toTelaOrdemServicoResponse(ordem));
    }

    @GetMapping()
    public ResponseEntity<HashMap<StatusVeiculo, ResponsePainelControle>> findDataForPainelControle(@Parameter(hidden = true) @AuthenticationPrincipal UsuarioDetalhesDto usuario) {
        Integer idOficina = usuario.getIdOficina();

        List<List<OrdemDeServico>> listaOrdensPorStatus = PAINEL_CONTROLE_SERVICE.findOrdensPorStatus(idOficina);
        return ResponseEntity.status(200).body(PainelControleMapper.toResponseList(listaOrdensPorStatus));
    }
}
