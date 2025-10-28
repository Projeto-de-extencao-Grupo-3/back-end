package geo.track.controller;

import geo.track.domain.Produtos;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.service.ProdutosService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import geo.track.exception.ExceptionBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints ultilizados para gerenciar os produtos da oficina.")
@SecurityRequirement(name = "Bearer")
public class ProdutosController {
    private final ProdutosService service;

    @Operation(summary = "Cadastrar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = Produtos.class))
            }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    public ResponseEntity<Produtos>cadastrar(@Valid @RequestBody Produtos prod){
        return ResponseEntity.status(201).body(service.cadastrar(prod));
    }

    @Operation(summary = "Listar todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso", content = {
                    @Content(schema = @Schema(implementation = Produtos.class))
            }),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping
    public ResponseEntity<List<Produtos>>listar(){
        List<Produtos>produtos = service.listar();

        if(produtos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos);
    }

    @Operation(summary = "Buscar produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = Produtos.class))
            }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Produtos>findProdutoById(@PathVariable Integer id){
        Produtos prod = service.findProdutoById(id);
        return ResponseEntity.status(200).body(prod);
    }

    @Operation(summary = "Atualizar dados de um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = Produtos.class))
            }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Produtos>putProdutos(@PathVariable Integer id, @RequestBody Produtos produtoAtt){
        Produtos prod = service.putProdutos(id,produtoAtt);
        return ResponseEntity.status(200).body(prod);
    }

    @Operation(summary = "Atualizar quantidade em estoque do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade atualizada com sucesso", content = {
                    @Content(schema = @Schema(implementation = Produtos.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PatchMapping("/quantidadeEstoque")
    public ResponseEntity<Produtos>patchQtdEstoque(@RequestBody RequestPatchQtdEstoque produtoAtt){
        Produtos prod = service.patchQtdEstoque(produtoAtt);
        return ResponseEntity.status(200).body(prod);
    }

    @Operation(summary = "Atualizar preço de compra do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preço de compra atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = Produtos.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/precoCompra")
    public ResponseEntity<Produtos>patchPrecoCompra(@RequestBody RequestPatchPrecoCompra produtoAtt){
        Produtos prod = service.patchPrecoCompra(produtoAtt);
        return ResponseEntity.status(200).body(prod);
    }

    @Operation(summary = "Atualizar preço de venda do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preço de venda atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = Produtos.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/precoVenda")
    public ResponseEntity<Produtos>patchPrecoVenda(@RequestBody RequestPatchPrecoVenda produtoAtt){
        Produtos prod = service.patchPrecoVenda(produtoAtt);
        return ResponseEntity.status(200).body(prod);
    }

    @Operation(summary = "Excluir um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>excluir(@PathVariable Integer id){
        service.excluir(id);
        return ResponseEntity.status(204).build();
    }

}
