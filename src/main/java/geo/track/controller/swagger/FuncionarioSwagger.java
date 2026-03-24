package geo.track.controller.swagger;

import geo.track.dto.funcionarios.request.RequestPostFuncionario;
import geo.track.dto.funcionarios.request.RequestPutFuncionario;
import geo.track.dto.funcionarios.response.FuncionarioResponse;
import geo.track.dto.oficinas.response.OficinaResponse;
import geo.track.infraestructure.exception.ExceptionBody;
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

@Tag(name = "Funcionários", description = "Endpoints utilizados para gerenciar os funcionários.")
@SecurityRequirement(name = "Bearer")
public interface FuncionarioSwagger {

    @Operation(summary = "Cadastrar um novo funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = FuncionarioResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    ResponseEntity<FuncionarioResponse> cadastrar(@RequestBody RequestPostFuncionario funcionario);

    @Operation(summary = "Buscar o funcionário pela fkOficina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = FuncionarioResponse.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/oficina/{idOficina}")
    ResponseEntity<List<FuncionarioResponse>> findByOficina(@PathVariable Integer idOficina);

    @Operation(summary = "Listar todas os Funcionários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Oficinas retornada com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OficinaResponse.class)))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping()
    ResponseEntity<List<FuncionarioResponse>> listarFuncionarios();

    @Operation(summary = "Buscar o funcionário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = FuncionarioResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<FuncionarioResponse> buscarPorId(@PathVariable Integer id);

    @Operation(summary = "Atualizar os dados de um funcionário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = FuncionarioResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PutMapping()
    ResponseEntity<FuncionarioResponse> atualizar(@RequestBody RequestPutFuncionario funcionario);

    @Operation(summary = "Excluir um funcionário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Funcionário excluído com sucesso (Sem Conteúdo)"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletar(@PathVariable Integer id);
}
