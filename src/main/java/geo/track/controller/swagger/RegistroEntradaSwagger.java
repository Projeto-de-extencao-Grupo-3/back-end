package geo.track.controller.swagger;

import geo.track.dto.registroEntrada.request.RequestPostEntrada;
import geo.track.dto.registroEntrada.request.RequestPostEntradaAgendada;
import geo.track.dto.registroEntrada.request.RequestPutRegistroEntrada;
import geo.track.dto.registroEntrada.response.RegistroEntradaResponse;
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

@Tag(name = "Registro de Entrada", description = "Endpoints para gerenciar os registros de entrada de veículos")
@SecurityRequirement(name = "Bearer")
public interface RegistroEntradaSwagger {

    @Operation(
            summary = "Realizar o Agendamento de um Veículo",
            description = "Recebe um objeto e o armazena, retornando o registro criado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Registro de entrada cadastrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = RegistroEntradaResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para o registro",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PostMapping("/agendamento")
    ResponseEntity<RegistroEntradaResponse> realizarAgendamentoVeiculo(@RequestBody RequestPostEntradaAgendada registroDTO);

    @Operation(
            summary = "Realizar a Entrada de Veículo Não-Agendado anteriormente",
            description = "Recebe um objeto com os novos dados do registro e o identificador e retornando o registro atualizado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Registro de entrada cadastrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = RegistroEntradaResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para a atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro não encontrado para atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PostMapping("/entrada")
    ResponseEntity<RegistroEntradaResponse> realizarEntradaVeiculo(@RequestBody RequestPostEntrada registroDTO);

    @Operation(
            summary = "Atualizar os dados de Entrada de Veículo agendado anteriormente",
            description = "Recebe um objeto com os novos dados do registro e o identificador e retornando o registro atualizado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Registro atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = RegistroEntradaResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para a atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro não encontrado para atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PutMapping("/atualizar")
    ResponseEntity<RegistroEntradaResponse> atualizarEntradaVeiculoAgendado(@RequestBody RequestPutRegistroEntrada registroDTO);

    @Operation(summary = "Buscar todos os registros de entrada")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Registros encontrados com sucesso",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RegistroEntradaResponse.class)))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Nenhum registro de entrada encontrado",
                    content = @Content
            )
    })
    @GetMapping
    ResponseEntity<List<RegistroEntradaResponse>> findRegistro();

    @Operation(summary = "Buscar registro de entrada pelo ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Registro encontrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = RegistroEntradaResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionBody.class))
            )
    })
    @GetMapping("/{idRegistro}")
    ResponseEntity<RegistroEntradaResponse> findRegistroById(@PathVariable Integer idRegistro);
}
