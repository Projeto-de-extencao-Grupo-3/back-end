package geo.track.gestao;

import geo.track.controller.swagger.ItemProdutoSwagger;
import geo.track.gestao.entity.ItemProduto;
import geo.track.dto.itensProdutos.ItemProdutoResponse;
import geo.track.dto.itensProdutos.RequestPutItemProduto;
import geo.track.gestao.service.produto.AtualizarItemProdutoUseCase;
import geo.track.gestao.service.produto.AdicionarItemProdutoUseCase;
import geo.track.gestao.service.produto.DeletarItemProdutoUseCase;
import geo.track.gestao.service.produto.RealizarBaixaEstoqueItemProdutoUseCase;
import geo.track.jornada.request.itens.RequestPostItemProduto;
import geo.track.gestao.util.ItemProdutoMapper;
import geo.track.service.ItemProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-produtos")
@RequiredArgsConstructor
public class ItemProdutoController implements ItemProdutoSwagger {
    private final ItemProdutoService ITEM_PRODUTO_SERVICE;
    private final AdicionarItemProdutoUseCase CADASTRAR_ITEM_PRODUTO_USECASE;
    private final AtualizarItemProdutoUseCase ATUALIZAR_ITEM_PRODUTO_USECASE;
    private final DeletarItemProdutoUseCase DELETAR_ITEM_PRODUTO_USECASE;
    private final RealizarBaixaEstoqueItemProdutoUseCase REALIZAR_BAIXA_ESTOQUE_ITEM_PRODUTO_USECASE;

    @Override
    @PostMapping
    public ResponseEntity<ItemProdutoResponse> save(@RequestBody @Valid RequestPostItemProduto body) {
        ItemProduto registroProduto = CADASTRAR_ITEM_PRODUTO_USECASE.execute(body);
        return ResponseEntity.status(201).body(ItemProdutoMapper.toResponse(registroProduto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ItemProdutoResponse>> findAll() {
        List<ItemProduto> registroProdutos = ITEM_PRODUTO_SERVICE.listarRegistros();
        if (registroProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ItemProdutoMapper.toResponse(registroProdutos));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ItemProdutoResponse> findById(@PathVariable Integer id) {
        ItemProduto registroProduto = ITEM_PRODUTO_SERVICE.buscarRegistroPorID(id);
        return ResponseEntity.status(200).body(ItemProdutoMapper.toResponse(registroProduto));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ItemProdutoResponse> put(@PathVariable Integer id, @RequestBody @Valid RequestPutItemProduto body) {
        ItemProduto registroProduto = ATUALIZAR_ITEM_PRODUTO_USECASE.execute(id, body);
        return ResponseEntity.status(200).body(ItemProdutoMapper.toResponse(registroProduto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        DELETAR_ITEM_PRODUTO_USECASE.execute(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/baixa-estoque/{id}")
    public ResponseEntity<ItemProdutoResponse> patchBaixaEstoque(@PathVariable Integer id) {
        REALIZAR_BAIXA_ESTOQUE_ITEM_PRODUTO_USECASE.execute(id);
        return ResponseEntity.status(200).body(null);
    }
}