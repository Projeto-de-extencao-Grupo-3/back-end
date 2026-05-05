package geo.track.catalogo.item_servico.infraestructure.web;

import geo.track.catalogo.item_servico.infraestructure.persistence.entity.*;
import geo.track.catalogo.item_servico.infraestructure.response.ItemServicoResponse;
import geo.track.catalogo.item_servico.infraestructure.request.RequestPutItemServico;
import geo.track.catalogo.item_servico.application.AdicionarItemServicoUseCase;
import geo.track.catalogo.item_servico.application.AtualizarItemServicoUseCase;
import geo.track.catalogo.item_servico.application.DeletarItemServicoUseCase;
import geo.track.catalogo.item_servico.infraestructure.ItemServicoMapper;
import geo.track.catalogo.item_servico.domain.ItemServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-servicos")
@RequiredArgsConstructor
public class ItemServicoController {
    private final ItemServicoService ITEM_SERVICO_SERVICE;
    private final AdicionarItemServicoUseCase CADASTRAR_ITEM_SERVICO_USECASE;
    private final AtualizarItemServicoUseCase ATUALIZAR_ITEM_SERVICO_USECASE;
    private final DeletarItemServicoUseCase DELETAR_ITEM_SERVICO_USECASE;

    @RequestMapping(value = "/partes_veiculo", method = RequestMethod.OPTIONS)
    public ResponseEntity<List<ParteVeiculo>> getPartesVeiculo() {
        return ResponseEntity.ok(List.of(ParteVeiculo.values()));
    }

    @RequestMapping(value = "/tipos_pintura", method = RequestMethod.OPTIONS)
    public ResponseEntity<List<TipoPintura>> getTiposPintura() {
        return ResponseEntity.ok(List.of(TipoPintura.values()));
    }

    @RequestMapping(value = "/tipos_servico", method = RequestMethod.OPTIONS)
    public ResponseEntity<List<Servico>> getTiposServico() {
        return ResponseEntity.ok(List.of(Servico.values()));
    }

    @RequestMapping(value = "/lados_veiculo", method = RequestMethod.OPTIONS)
    public ResponseEntity<List<LadoVeiculo>> getLadosVeiculo() {
        return ResponseEntity.ok(List.of(LadoVeiculo.values()));
    }

    @GetMapping
    public ResponseEntity<List<ItemServicoResponse>> findAll() {
        List<ItemServico> itensServicos = ITEM_SERVICO_SERVICE.listarItensServicos();
        if (itensServicos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itensServicos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemServicoResponse> findById(@PathVariable Integer id) {
        ItemServico itemServico = ITEM_SERVICO_SERVICE.buscarItemServicoPorId(id);
        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itemServico));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemServicoResponse> atualizar(@PathVariable Integer id, @RequestBody @Valid RequestPutItemServico body) {
        ItemServico itemServico = ATUALIZAR_ITEM_SERVICO_USECASE.execute(id, body);
        return ResponseEntity.status(200).body(ItemServicoMapper.toResponse(itemServico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        DELETAR_ITEM_SERVICO_USECASE.execute(id);
        return ResponseEntity.status(204).build();
    }
}