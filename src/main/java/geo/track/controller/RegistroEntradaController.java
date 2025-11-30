package geo.track.controller;

import geo.track.domain.RegistroEntrada;
import geo.track.dto.registroEntrada.request.RequestPostEntrada;
import geo.track.dto.registroEntrada.request.RequestPostEntradaAgendada;
import geo.track.dto.registroEntrada.request.RequestPutRegistroEntrada;
import geo.track.dto.registroEntrada.response.RegistroEntradaResponse;
import geo.track.exception.ExceptionBody;
import geo.track.mapper.RegistroEntradaMapper;
import geo.track.service.RegistroEntradaService;
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
@RequestMapping("/entrada")
@RequiredArgsConstructor
@Tag(name = "Registro de Entrada", description = "Endpoints para gerenciar os registros de entrada de veículos")
@SecurityRequirement(name = "Bearer")
public class RegistroEntradaController {
    private final RegistroEntradaService entradaService;

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
    public ResponseEntity<RegistroEntradaResponse> realizarAgendamentoVeiculo(@RequestBody RequestPostEntradaAgendada registroDTO){
        RegistroEntrada registro = entradaService.realizarAgendamentoVeiculo(registroDTO);
        return ResponseEntity.status(201).body(RegistroEntradaMapper.toResponse(registro));
    }

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
    public ResponseEntity<RegistroEntradaResponse> realizarEntradaVeiculo(@RequestBody RequestPostEntrada registroDTO){
        RegistroEntrada registro = entradaService.realizarEntradaVeiculo(registroDTO);
        return ResponseEntity.status(201).body(RegistroEntradaMapper.toResponse(registro));
    }

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
    public ResponseEntity<RegistroEntradaResponse> atualizarEntradaVeiculoAgendado(@RequestBody RequestPutRegistroEntrada registroDTO){
        RegistroEntrada registro = entradaService.atualizarEntradaVeiculoAgendado(registroDTO);
        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(registro));
    }

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
    public ResponseEntity<List<RegistroEntradaResponse>> findRegistro(){
        List<RegistroEntrada> registro = entradaService.findRegistros();
        if (registro.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(registro));
    }

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
    public ResponseEntity<RegistroEntradaResponse> findRegistroById(@PathVariable Integer idRegistro){
        RegistroEntrada registro = entradaService.findRegistroById(idRegistro);
        return ResponseEntity.status(200).body(RegistroEntradaMapper.toResponse(registro));
    }

    @Operation(summary = "Remover um registro de entrada")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Registro removido com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro não encontrado para remoção",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @DeleteMapping("/{idRegistro}")
    public ResponseEntity<Void> deleteRegistro(@PathVariable Integer idRegistro){
        entradaService.deletarRegistro(idRegistro);
        return ResponseEntity.status(204).build();
    }
}