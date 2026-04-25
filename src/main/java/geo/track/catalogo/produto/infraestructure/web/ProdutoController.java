package geo.track.catalogo.produto.infraestructure.web;

import geo.track.catalogo.produto.application.*;
import geo.track.catalogo.produto.domain.entity.Produto;
import geo.track.catalogo.produto.infraestructure.request.ProdutoRequest;
import geo.track.catalogo.produto.infraestructure.response.ProdutoResponse;
import geo.track.catalogo.produto.infraestructure.request.RequestPatchPrecoCompra;
import geo.track.catalogo.produto.infraestructure.request.RequestPatchPrecoVenda;
import geo.track.catalogo.item_servico.infraestructure.persistence.entity.Servico;
import geo.track.catalogo.produto.infraestructure.ProdutoMapper;
import geo.track.catalogo.produto.domain.ProdutoService;
import geo.track.gestao.cliente.infraestructure.ClientesMapper;
import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.response.cliente.ClienteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
        List<Produto> produtos = PRODUTO_SERVICE.listarProdutos();
        if (nome != null) {
            produtos = produtos.stream().filter(p -> p.getNome().contains(nome)).toList();
        }

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(produtos));
    }

    @GetMapping("/busca/nome")
    public ResponseEntity<Page<ProdutoResponse>> findAllProdutosPorNomePaginado(
            @PageableDefault(size = 8, sort = "idProduto") Pageable pageable,
            @RequestParam(required = true) String nome
    ) {
        Page<Produto> produtos = PRODUTO_SERVICE.listarProdutosPorNomePaginado(nome, pageable);

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        Page<ProdutoResponse> response = produtos.map(ProdutoMapper::toResponse);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/status")
    public ResponseEntity<HashMap<String, List<ProdutoResponse>>> listarPorStatus() {
        HashMap<String, List<Produto>> produtos = PRODUTO_SERVICE.listarChaveadaProdutosPorStatus();

        HashMap<String, List<ProdutoResponse>> response = ProdutoMapper.toResponse(produtos);

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/produtos-paginados")
    public ResponseEntity<Page<ProdutoResponse>> listarTodos(
            @PageableDefault(size = 8, sort = "idProduto") Pageable pageable) {

        Page<Produto> produtos = PRODUTO_SERVICE.listarProdutosPaginados(pageable);

        Page<ProdutoResponse> response = produtos.map(ProdutoMapper::toResponse);

        if (response.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/por-servico")
    public ResponseEntity<Page<ProdutoResponse>> listarPorTipo(
            @RequestParam Servico tipo,
            @PageableDefault(size = 6, sort = "idProduto") Pageable pageable) {

        Page<Produto> produtos = PRODUTO_SERVICE.listarProdutosPorTipoServico(tipo, pageable);

        Page<ProdutoResponse> response = produtos.map(ProdutoMapper::toResponse);

        if (response.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> findProdutoById(@PathVariable Integer id) {
        Produto prod = PRODUTO_SERVICE.buscarProdutosPorId(id);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> putProdutos(@PathVariable Integer id, @Valid @RequestBody ProdutoRequest body) {
        Produto prod = ATUALIZAR_PRODUTO_USECASE.execute(id, body);
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