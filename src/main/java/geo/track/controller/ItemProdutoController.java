package geo.track.controller;

import geo.track.controller.swagger.ItemProdutoSwagger;
import geo.track.domain.ItemProduto;
import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensProdutos.RequestPostItemProduto;
import geo.track.dto.itensProdutos.RequestPutItemProduto;
import geo.track.mapper.ItemProdutoMapper;
import geo.track.service.ItemProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itensProdutos")
@RequiredArgsConstructor
public class ItemProdutoController implements ItemProdutoSwagger {

    private final ItemProdutoService service;

    @Override
    @PostMapping
    public ResponseEntity<ItemProdutoResponse> save(@RequestBody RequestPostItemProduto body) {
        ItemProduto registroProduto = service.cadastrarRegistro(body);
        return ResponseEntity.status(201).body(ItemProdutoMapper.toResponse(registroProduto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ItemProdutoResponse>> findAll() {
        List<ItemProduto> registroProdutos = service.listarRegistros();
        if (registroProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ItemProdutoMapper.toResponse(registroProdutos));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ItemProdutoResponse> findById(@PathVariable Integer id) {
        ItemProduto registroProduto = service.buscarRegistroPorID(id);
        return ResponseEntity.status(200).body(ItemProdutoMapper.toResponse(registroProduto));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ItemProdutoResponse> put(@PathVariable Integer id, @RequestBody RequestPutItemProduto body) {
        ItemProduto registroProduto = service.atualizarRegistro(id, body);
        return ResponseEntity.status(200).body(ItemProdutoMapper.toResponse(registroProduto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deletarRegistro(id);
        return ResponseEntity.status(204).build();
    }
}