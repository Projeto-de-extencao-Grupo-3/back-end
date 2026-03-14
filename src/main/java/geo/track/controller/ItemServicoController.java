package geo.track.controller;

import geo.track.domain.ItemServico;
import geo.track.domain.OrdemDeServico;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.dto.itensServicos.RequestPostItemServico;
import geo.track.mapper.ItemServicoMapper;
import geo.track.service.ItemServicoService;
import geo.track.service.OrdemDeServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-servicos")
@RequiredArgsConstructor
public class ItemServicoController {
    private final ItemServicoService ITEM_SERVICO_SERVICE;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @PostMapping
    public ResponseEntity<ItemServicoResponse> cadastrar(@AuthenticationPrincipal UsuarioDetalhesDto usuario, @RequestBody @Valid RequestPostItemServico body) {
        Integer idOficina = usuario.getIdOficina();

        OrdemDeServico ordemServico = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(body.getFkOrdemServico(), idOficina);

        ItemServico item = ItemServicoMapper.toDomain(body, ordemServico, body.getTipoServico());

        ItemServico itemServico = ITEM_SERVICO_SERVICE.cadastrar(item);
        return ResponseEntity.status(201).body(ItemServicoMapper.toResponse(itemServico));
    }

    @GetMapping
    public ResponseEntity<List<ItemServicoResponse>> findAll() {
        List<ItemServico> itensServicos = ITEM_SERVICO_SERVICE.listar();
        if (itensServicos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itensServicos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemServicoResponse> findById(@PathVariable Integer id) {
        ItemServico itemServico = ITEM_SERVICO_SERVICE.findById(id);
        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itemServico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemServicoResponse> atualizar(@PathVariable Integer id, @RequestBody ItemServico itens) {
        ItemServico itemServico = ITEM_SERVICO_SERVICE.atualizar(id, itens);
        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itemServico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ITEM_SERVICO_SERVICE.delete(id);
        return ResponseEntity.status(204).build();
    }
}