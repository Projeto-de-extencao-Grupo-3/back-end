package geo.track.controller;

import geo.track.domain.OrdemDeServico;
import geo.track.dto.os.response.ServicoProdutoOrdemResponse;
import geo.track.dto.painelControle.response.ResponsePainelControle;
import geo.track.enums.os.StatusVeiculo;
import geo.track.mapper.OrdemDeServicoMapper;
import geo.track.mapper.PainelControleMapper;
import geo.track.service.OrdemDeServicoService;
import geo.track.service.PainelControleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/painel-controle")
@RequiredArgsConstructor
public class PainelControleController {
    private final OrdemDeServicoService ordemService;
    private final PainelControleService painelService;

    @GetMapping("/servicos-produtos/{idOrdem}")
    public ResponseEntity<ServicoProdutoOrdemResponse> findById(@PathVariable Integer idOrdem) {
        OrdemDeServico ordem = ordemService.findOrdemById(idOrdem);

        if (ordem.getServicos().isEmpty() && ordem.getProdutos().isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toServicoProdutoResponse(ordem));
    }

    @GetMapping()
    public ResponseEntity<HashMap<StatusVeiculo, ResponsePainelControle>> findDataForPainelControle() {
        List<List<OrdemDeServico>> listaOrdensPorStatus = painelService.findOrdensPorStatus();
        return ResponseEntity.status(200).body(PainelControleMapper.toResponseList(listaOrdensPorStatus));
    }
}
