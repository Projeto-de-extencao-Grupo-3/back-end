package geo.track.controller;

import geo.track.domain.Produto;
import geo.track.dto.produtos.ProdutoResponse;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.mapper.ProdutoMapper;
import geo.track.service.ProdutoService;
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
import io.swagger.v3.oas.annotations.media.ArraySchema;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints ultilizados para gerenciar os produtos da oficina.")
@SecurityRequirement(name = "Bearer")
public class ProdutoController {
    private final ProdutoService service;

    @Operation(summary = "Cadastrar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    public ResponseEntity<ProdutoResponse>cadastrar(@Valid @RequestBody Produto prod){
        Produto novoProduto = service.cadastrar(prod);
        return ResponseEntity.status(201).body(ProdutoMapper.toResponse(novoProduto));
    }

    @Operation(summary = "Listar todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ProdutoResponse.class)))
            }),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>>listar(){
        List<Produto>produtos = service.listar();

        if(produtos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(produtos));
    }

    @Operation(summary = "Buscar produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse>findProdutoById(@PathVariable Integer id){
        Produto prod = service.findProdutoById(id);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Operation(summary = "Atualizar dados de um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse>putProdutos(@PathVariable Integer id, @RequestBody Produto produtoAtt){
        Produto prod = service.putProdutos(id,produtoAtt);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Operation(summary = "Atualizar quantidade em estoque do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade atualizada com sucesso", content = {
                    @Content(schema = @Schema(implementation = ProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PatchMapping("/quantidadeEstoque")
    public ResponseEntity<ProdutoResponse>patchQtdEstoque(@RequestBody RequestPatchQtdEstoque produtoAtt){
        Produto prod = service.patchQtdEstoque(produtoAtt);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Operation(summary = "Atualizar preço de compra do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preço de compra atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/precoCompra")
    public ResponseEntity<ProdutoResponse>patchPrecoCompra(@RequestBody RequestPatchPrecoCompra produtoAtt){
        Produto prod = service.patchPrecoCompra(produtoAtt);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
    }

    @Operation(summary = "Atualizar preço de venda do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preço de venda atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/precoVenda")
    public ResponseEntity<ProdutoResponse>patchPrecoVenda(@RequestBody RequestPatchPrecoVenda produtoAtt){
        Produto prod = service.patchPrecoVenda(produtoAtt);
        return ResponseEntity.status(200).body(ProdutoMapper.toResponse(prod));
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