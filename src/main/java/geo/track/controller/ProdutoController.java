package geo.track.controller;

import geo.track.controller.swagger.ProdutoSwagger;
import geo.track.domain.Produto;
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
    private final ProdutoService service;

    @Override
    @PostMapping
    public ResponseEntity<ProdutoResponse>cadastrar(@Valid @RequestBody Produto prod){
        Produto novoProduto = service.cadastrar(prod);
        return ResponseEntity.status(201).body(ProdutoMapper.toResponse(novoProduto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>>listar(){
        List<Produto>produtos = service.listar();

        if(produtos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(produtos));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse>findProdutoById(@PathVariable Integer id){
        Produto prod = service.findProdutoById(id);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse>putProdutos(@PathVariable Integer id, @RequestBody Produto produtoAtt){
        Produto prod = service.putProdutos(id,produtoAtt);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @PatchMapping("/quantidadeEstoque")
    public ResponseEntity<ProdutoResponse>patchQtdEstoque(@RequestBody RequestPatchQtdEstoque produtoAtt){
        Produto prod = service.patchQtdEstoque(produtoAtt);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @PatchMapping("/precoCompra")
    public ResponseEntity<ProdutoResponse>patchPrecoCompra(@RequestBody RequestPatchPrecoCompra produtoAtt){
        Produto prod = service.patchPrecoCompra(produtoAtt);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @PatchMapping("/precoVenda")
    public ResponseEntity<ProdutoResponse>patchPrecoVenda(@RequestBody RequestPatchPrecoVenda produtoAtt){
        Produto prod = service.patchPrecoVenda(produtoAtt);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>excluir(@PathVariable Integer id){
        service.excluir(id);
        return ResponseEntity.status(204).build();
    }
}