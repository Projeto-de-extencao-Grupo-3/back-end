package geo.track.catalogo.item_produto.infraestructure.web;

import geo.track.catalogo.item_produto.infraestructure.response.ItemProdutoResponse;
import geo.track.catalogo.item_produto.infraestructure.request.RequestPutItemProduto;
import geo.track.infraestructure.exception.ExceptionBody;
import geo.track.catalogo.item_produto.infraestructure.request.RequestPostItemProduto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ItensProdutos", description = "Endpoints utilizados para gerenciar os itens de produtos de uma ordem de serviço.")
@SecurityRequirement(name = "Bearer")
public interface ItemProdutoSwagger {

    @Operation(summary = "Cadastrar um novo item de produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item de produto cadastrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ItemProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    ResponseEntity<ItemProdutoResponse> save(@RequestBody RequestPostItemProduto body);

    @Operation(summary = "Listar todos os itens de produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens de produto encontrados com sucesso", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ItemProdutoResponse.class)))
            }),
            @ApiResponse(responseCode = "204", description = "Nenhum item de produto encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping
    ResponseEntity<List<ItemProdutoResponse>> findAll();

    @Operation(summary = "Buscar item de produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de produto encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ItemProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Item de produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<ItemProdutoResponse> findById(@PathVariable Integer id);

    @Operation(summary = "Atualizar item de produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de produto atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ItemProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Item de produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PutMapping("/{id}")
    ResponseEntity<ItemProdutoResponse> put(@PathVariable Integer id, @RequestBody RequestPutItemProduto body);

    @Operation(summary = "Deletar item de produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item de produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id);
}
