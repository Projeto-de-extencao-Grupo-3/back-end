package geo.track.controller;

import geo.track.domain.Produtos;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.service.ProdutosService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {
    final ProdutosService service;

    public ProdutosController(ProdutosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Produtos>cadastrar(@Valid @RequestBody Produtos prod){
        return ResponseEntity.status(201).body(service.cadastrar(prod));
    }

    @GetMapping
    public ResponseEntity<List<Produtos>>listar(){
        List<Produtos>produtos = service.listar();

        if(produtos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produtos>findProdutoById(@PathVariable Integer id){
        Produtos prod = service.findProdutoById(id);
        return ResponseEntity.status(200).body(prod);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produtos>putProdutos(@PathVariable Integer id, @RequestBody Produtos produtoAtt){
        Produtos prod = service.putProdutos(id,produtoAtt);
        return ResponseEntity.status(200).body(prod);
    }

    @PatchMapping("/quantidadeEstoque")
    public ResponseEntity<Produtos>patchQtdEstoque(@RequestBody RequestPatchQtdEstoque produtoAtt){
        Produtos prod = service.patchQtdEstoque(produtoAtt);
        return ResponseEntity.status(200).body(prod);
    }

    @PatchMapping("/precoCompra")
    public ResponseEntity<Produtos>patchPrecoCompra(@RequestBody RequestPatchPrecoCompra produtoAtt){
        Produtos prod = service.patchPrecoCompra(produtoAtt);
        return ResponseEntity.status(200).body(prod);
    }

    @PatchMapping("/precoVenda")
    public ResponseEntity<Produtos>patchPrecoVenda(@RequestBody RequestPatchPrecoVenda produtoAtt){
        Produtos prod = service.patchPrecoVenda(produtoAtt);
        return ResponseEntity.status(200).body(prod);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>excluir(@PathVariable Integer id){
        service.excluir(id);
        return ResponseEntity.status(204).build();
    }






}
