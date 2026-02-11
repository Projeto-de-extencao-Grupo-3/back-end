package geo.track.controller;

import geo.track.controller.swagger.ItemServicoSwagger;
import geo.track.domain.ItemServico;
import geo.track.domain.OrdemDeServico;
import geo.track.domain.Servico;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.dto.itensServicos.RequestPostItemServico;
import geo.track.mapper.ItemServicoMapper;
import geo.track.service.ItemServicoService;
import geo.track.service.OrdemDeServicoService;
import geo.track.service.ServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itensServico")
@RequiredArgsConstructor
public class ItemServicoController implements ItemServicoSwagger {

    private final ItemServicoService REGISTRO_SERVICO_SERVICE;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final ServicoService SERVICO_SERVICE;

    @Override
    @PostMapping
    public ResponseEntity<ItemServicoResponse> cadastrar(@RequestBody RequestPostItemServico body) {
        OrdemDeServico ordemServico = ORDEM_SERVICO_SERVICE.findOrdemById(body.getFkOrdemServico());
        Servico servico = SERVICO_SERVICE.buscarPorId(body.getFkServico());
        ItemServico item = ItemServicoMapper.toDomain(body, ordemServico, servico);

        ItemServico itemServico = REGISTRO_SERVICO_SERVICE.cadastrar(item);
        return ResponseEntity.status(201).body(ItemServicoMapper.toResponse(itemServico));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ItemServicoResponse>> findAll() {
        List<ItemServico> itensServicos = REGISTRO_SERVICO_SERVICE.listar();
        if (itensServicos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itensServicos));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ItemServicoResponse> findById(@PathVariable Integer id) {
        ItemServico itemServico = REGISTRO_SERVICO_SERVICE.findById(id);
        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itemServico));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ItemServicoResponse> atualizar(@PathVariable Integer id, @RequestBody ItemServico itens) {
        ItemServico itemServico = REGISTRO_SERVICO_SERVICE.atualizar(id, itens);
        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itemServico));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        REGISTRO_SERVICO_SERVICE.delete(id);
        return ResponseEntity.status(204).build();
    }
}