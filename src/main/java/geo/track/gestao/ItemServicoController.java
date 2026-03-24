package geo.track.gestao;

import geo.track.gestao.entity.ItemServico;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.dto.itensServicos.RequestPutItemServico;
import geo.track.jornada.request.itens.RequestPostItemServico;
import geo.track.gestao.util.ItemServicoMapper;
import geo.track.service.ItemServicoService;
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

    @PostMapping
    public ResponseEntity<ItemServicoResponse> cadastrar(@AuthenticationPrincipal UsuarioDetalhesDto usuario, @RequestBody @Valid RequestPostItemServico body) {
        Integer idOficina = usuario.getIdOficina();

        ItemServico itemServico = ITEM_SERVICO_SERVICE.cadastrar(body, idOficina);
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
    public ResponseEntity<ItemServicoResponse> atualizar(@PathVariable Integer id, @RequestBody @Valid RequestPutItemServico body) {
        ItemServico itemServico = ITEM_SERVICO_SERVICE.atualizar(id, body);
        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itemServico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ITEM_SERVICO_SERVICE.delete(id);
        return ResponseEntity.status(204).build();
    }
}