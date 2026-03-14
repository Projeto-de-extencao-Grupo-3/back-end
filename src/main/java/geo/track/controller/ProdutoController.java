package geo.track.controller;

import geo.track.controller.swagger.ProdutoSwagger;
import geo.track.domain.Produto;
import geo.track.dto.produtos.ProdutoRequest;
import geo.track.dto.produtos.ProdutoResponse;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.mapper.ProdutoMapper;
import geo.track.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController implements ProdutoSwagger {
    private final ProdutoService PRODUTO_SERVICE;

    @Override
    @PostMapping
    public ResponseEntity<ProdutoResponse>cadastrar(@Valid @RequestBody ProdutoRequest body){
        Produto novoProduto = PRODUTO_SERVICE.cadastrar(body);
        return ResponseEntity.status(201).body(ProdutoMapper.toResponse(novoProduto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>>listar(){
        List<Produto>produtos = PRODUTO_SERVICE.listar();

        if(produtos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(produtos));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse>findProdutoById(@PathVariable Integer id){
        Produto prod = PRODUTO_SERVICE.findProdutoById(id);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse>putProdutos(@PathVariable Integer id, @Valid @RequestBody ProdutoRequest body){
        Produto prod = PRODUTO_SERVICE.putProdutos(id,body);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @PatchMapping("/quantidade-estoque")
    public ResponseEntity<ProdutoResponse>patchQtdEstoque(@RequestBody @Valid RequestPatchQtdEstoque body){
        Produto prod = PRODUTO_SERVICE.patchQtdEstoque(body);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @PatchMapping("/preco-compra")
    public ResponseEntity<ProdutoResponse>patchPrecoCompra(@RequestBody @Valid RequestPatchPrecoCompra body){
        Produto prod = PRODUTO_SERVICE.patchPrecoCompra(body);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @PatchMapping("/preco-venda")
    public ResponseEntity<ProdutoResponse>patchPrecoVenda(@RequestBody @Valid RequestPatchPrecoVenda body){
        Produto prod = PRODUTO_SERVICE.patchPrecoVenda(body);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>excluir(@PathVariable Integer id){
        PRODUTO_SERVICE.excluir(id);
        return ResponseEntity.status(204).build();
    }
}