package geo.track.controller.swagger;

import geo.track.domain.ItemServico;
import geo.track.dto.itensServicos.ItemServicoResponse;
import geo.track.dto.itensServicos.RequestPostItemServico;
import geo.track.exception.ExceptionBody;
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

@Tag(name = "ItensServicos", description = "Endpoints utilizados para gerenciar ItensServicos.")
@SecurityRequirement(name = "Bearer")
public interface ItemServicoSwagger {

    @Operation(summary = "Cadastrar ItensServico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item de Serviço cadastrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ItemServicoResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    ResponseEntity<ItemServicoResponse> cadastrar(@RequestBody RequestPostItemServico body);

    @Operation(summary = "Buscar todos os Itens Serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens de Serviço encontrados com sucesso", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ItemServicoResponse.class)))
            }),
            @ApiResponse(responseCode = "204", description = "Nenhum item de serviço encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping
    ResponseEntity<List<ItemServicoResponse>> findAll();

    @Operation(summary = "Buscar Itens Serviço por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de Serviço encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ItemServicoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Item de Serviço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<ItemServicoResponse> findById(@PathVariable Integer id);

    @Operation(summary = "Atualizar Itens Serviço por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de Serviço atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = ItemServicoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Item de Serviço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PutMapping("/{id}")
    ResponseEntity<ItemServicoResponse> atualizar(@PathVariable Integer id, @RequestBody ItemServico itens);

    @Operation(summary = "Deletar Itens Serviço por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item de Serviço deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de Serviço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id);
}
