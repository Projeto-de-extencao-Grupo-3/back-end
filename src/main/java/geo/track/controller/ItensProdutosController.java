package geo.track.controller;

import geo.track.domain.ItensProdutos;
import geo.track.dto.itensprodutos.ItensProdutoResponse;
import geo.track.dto.itensProdutos.RequestPostItemProduto;
import geo.track.dto.itensProdutos.RequestPutItemProduto;
import geo.track.exception.ExceptionBody;
import geo.track.mapper.ItensProdutoMapper;
import geo.track.service.ItensProdutosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itensProdutos")
@SecurityRequirement(name = "Bearer")
@RequiredArgsConstructor
@Tag(name = "ItensProdutos", description = "Endpoints utilizados para gerenciar os itens de produtos de uma ordem de serviço.")
public class ItensProdutosController {

    private final ItensProdutosService service;

    @Operation(summary = "Cadastrar um novo item de produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item de produto cadastrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ItensProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    public ResponseEntity<ItensProdutoResponse> save(@RequestBody RequestPostItemProduto body) {
        ItensProdutos registroProduto = service.cadastrarRegistro(body);
        return ResponseEntity.status(201).body(ItensProdutoMapper.toResponse(registroProduto));
    }

    @Operation(summary = "Listar todos os itens de produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens de produto encontrados com sucesso", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ItensProdutoResponse.class)))
            }),
            @ApiResponse(responseCode = "204", description = "Nenhum item de produto encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping
    public ResponseEntity<List<ItensProdutoResponse>> findAll() {
        List<ItensProdutos> registroProdutos = service.listarRegistros();
        if (registroProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ItensProdutoMapper.toResponse(registroProdutos));
    }

    @Operation(summary = "Buscar item de produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de produto encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ItensProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Item de produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItensProdutoResponse> findById(@PathVariable Integer id) {
        ItensProdutos registroProduto = service.buscarRegistroPorID(id);
        return ResponseEntity.status(200).body(ItensProdutoMapper.toResponse(registroProduto));
    }

    @Operation(summary = "Atualizar item de produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de produto atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ItensProdutoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Item de produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ItensProdutoResponse> put(@PathVariable Integer id, @RequestBody RequestPutItemProduto body) {
        ItensProdutos registroProduto = service.atualizarRegistro(id, body);
        return ResponseEntity.status(200).body(ItensProdutoMapper.toResponse(registroProduto));
    }

    @Operation(summary = "Deletar item de produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item de produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deletarRegistro(id);
        return ResponseEntity.status(204).build();
    }
}