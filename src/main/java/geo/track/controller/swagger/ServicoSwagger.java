package geo.track.controller.swagger;

import geo.track.domain.Servico;
import geo.track.dto.servicos.ServicoResponse;
import geo.track.exception.ExceptionBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Serviços", description = "Endpoints utilizados para gerenciar os serviços")
@SecurityRequirement(name = "Bearer")
public interface ServicoSwagger {

    @Operation(
            summary = "Cadastrar novo serviço",
            description = "Recebe um objeto de serviço e o armazena, retornando o serviço criado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Serviço cadastrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = ServicoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para o serviço",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PostMapping
    ResponseEntity<ServicoResponse> cadastrar(@RequestBody Servico servico);

    @Operation(summary = "Buscar serviço pelo ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Serviço encontrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = ServicoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Serviço não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionBody.class))
            )
    })
    @GetMapping("/{id}")
    ResponseEntity<ServicoResponse> buscarPorId(@PathVariable Integer id);

    @Operation(
            summary = "Atualizar completamente um serviço",
            description = "Recebe o ID e um objeto de serviço com os novos dados para atualização."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Serviço atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = ServicoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para a atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Serviço não encontrado para atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PutMapping("/{id}")
    ResponseEntity<ServicoResponse> atualizar(@PathVariable Integer id, @RequestBody Servico servico);

    @Operation(summary = "Remover um serviço")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Serviço removido com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Serviço não encontrado para remoção",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletar(@PathVariable Integer id);
}
