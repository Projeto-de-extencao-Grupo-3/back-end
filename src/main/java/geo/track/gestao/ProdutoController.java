package geo.track.gestao;

import geo.track.controller.swagger.ProdutoSwagger;
import geo.track.gestao.entity.Produto;
import geo.track.dto.produtos.ProdutoRequest;
import geo.track.dto.produtos.ProdutoResponse;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.gestao.service.produto.*;
import geo.track.gestao.util.ProdutoMapper;
import geo.track.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController implements ProdutoSwagger {
    private final ProdutoService PRODUTO_SERVICE;
    private final CadastrarProdutoUseCase CADASTRAR_PRODUTO_USECASE;
    private final AtualizarProdutoUseCase ATUALIZAR_PRODUTO_USECASE;
    private final AlterarQuantidadeEstoqueProdutoUseCase ALTERAR_QUANTIDADE_ESTOQUE_PRODUTO_USECASE;
    private final AlterarPrecoCompraProdutoUseCase ALTERAR_PRECO_COMPRA_PRODUTO_USECASE;
    private final AlterarPrecoVendaProdutoUseCase ALTERAR_PRECO_VENDA_PRODUTO_USECASE;
    private final DeletarProdutoUseCase DELETAR_PRODUTO_USECASE;

    @Override
    @PostMapping
    public ResponseEntity<ProdutoResponse> cadastrar(@Valid @RequestBody ProdutoRequest body) {
        Produto novoProduto = CADASTRAR_PRODUTO_USECASE.execute(body);
        return ResponseEntity.status(201).body(ProdutoMapper.toResponse(novoProduto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listarContains(@RequestParam(required = false) String nome) {
        List<Produto> produtos = PRODUTO_SERVICE.listar();
        if (nome != null) {
            produtos = produtos.stream().filter(p -> p.getNome().contains(nome)).toList();
        }

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(produtos));
    }

    @GetMapping("/status")
    public ResponseEntity<HashMap<String, List<ProdutoResponse>>> listarPorStatus() {
        HashMap<String, List<Produto>> produtos = PRODUTO_SERVICE.listarProdutosPorStatus();

        HashMap<String, List<ProdutoResponse>> response = ProdutoMapper.toResponse(produtos);

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> findProdutoById(@PathVariable Integer id) {
        Produto prod = PRODUTO_SERVICE.findProdutoById(id);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> putProdutos(@PathVariable Integer id, @Valid @RequestBody ProdutoRequest body) {
        Produto prod = ATUALIZAR_PRODUTO_USECASE.execute(id, body);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @PatchMapping("/quantidade-estoque")
    public ResponseEntity<ProdutoResponse> patchQtdEstoque(@RequestBody @Valid RequestPatchQtdEstoque body) {
        Produto prod = ALTERAR_QUANTIDADE_ESTOQUE_PRODUTO_USECASE.execute(body);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @PatchMapping("/preco-compra")
    public ResponseEntity<ProdutoResponse> patchPrecoCompra(@RequestBody @Valid RequestPatchPrecoCompra body) {
        Produto prod = ALTERAR_PRECO_COMPRA_PRODUTO_USECASE.execute(body);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @PatchMapping("/preco-venda")
    public ResponseEntity<ProdutoResponse> patchPrecoVenda(@RequestBody @Valid RequestPatchPrecoVenda body) {
        Produto prod = ALTERAR_PRECO_VENDA_PRODUTO_USECASE.execute(body);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        DELETAR_PRODUTO_USECASE.execute(id);
        return ResponseEntity.status(204).build();
    }
}