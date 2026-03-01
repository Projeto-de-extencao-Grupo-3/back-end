package geo.track.controller.swagger;

import geo.track.domain.Produto;
import geo.track.dto.produtos.ProdutoResponse;
import geo.track.dto.produtos.RequestPatchPrecoCompra;
import geo.track.dto.produtos.RequestPatchPrecoVenda;
import geo.track.dto.produtos.RequestPatchQtdEstoque;
import geo.track.exception.ExceptionBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Produtos", description = "Endpoints ultilizados para gerenciar os produtos da oficina.")
@SecurityRequirement(name = "Bearer")
public interface ProdutoSwagger {

    @Operation(summary = "Cadastrar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    ResponseEntity<ProdutoResponse> cadastrar(@Valid @RequestBody Produto prod);

    @Operation(summary = "Listar todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ProdutoResponse.class)))
            }),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping
    ResponseEntity<List<ProdutoResponse>> listar();

    @Operation(summary = "Buscar produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<ProdutoResponse> findProdutoById(@PathVariable Integer id);

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
    ResponseEntity<ProdutoResponse> putProdutos(@PathVariable Integer id, @RequestBody Produto produtoAtt);

    @Operation(summary = "Atualizar quantidade em estoque do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade atualizada com sucesso", content = {
                    @Content(schema = @Schema(implementation = ProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PatchMapping("/quantidadeEstoque")
    ResponseEntity<ProdutoResponse> patchQtdEstoque(@RequestBody RequestPatchQtdEstoque produtoAtt);

    @Operation(summary = "Atualizar preço de compra do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preço de compra atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/precoCompra")
    ResponseEntity<ProdutoResponse> patchPrecoCompra(@RequestBody RequestPatchPrecoCompra produtoAtt);

    @Operation(summary = "Atualizar preço de venda do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preço de venda atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/precoVenda")
    ResponseEntity<ProdutoResponse> patchPrecoVenda(@RequestBody RequestPatchPrecoVenda produtoAtt);

    @Operation(summary = "Excluir um produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> excluir(@PathVariable Integer id);
}
