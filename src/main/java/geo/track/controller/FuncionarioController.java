package geo.track.controller;

import geo.track.domain.Funcionarios;
import geo.track.exception.ExceptionBody;
import geo.track.service.FuncionariosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/funcionarios")
@Tag(name = "Funcionários", description = "Endpoints utilizados para gerenciar os funcionários.")
@SecurityRequirement(name = "Bearer")
public class FuncionarioController {

    private final FuncionariosService service;

    @Operation(summary = "Cadastrar um novo funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = Funcionarios.class))
            }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    public ResponseEntity<Funcionarios> cadastrar(@RequestBody Funcionarios funcionario){
        Funcionarios funcionarioResposta = service.cadastrar(funcionario);
        return ResponseEntity.status(201).body(funcionarioResposta);
    }

    @Operation(summary = "Buscar o funcionário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = Funcionarios.class))
            }),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Funcionarios> buscarPorId(@PathVariable Integer id){
        Funcionarios funcionarioResposta = service.buscarPorId(id);
        return ResponseEntity.status(200).body(funcionarioResposta);
    }

    @Operation(summary = "Atualizar os dados de um funcionário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = Funcionarios.class))
            }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Funcionarios> atualizar(@PathVariable Integer id, @RequestBody Funcionarios funcionario){
        Funcionarios funcionarioResposta = service.atualizar(id, funcionario);
        return ResponseEntity.status(200).body(funcionarioResposta);
    }

    @Operation(summary = "Excluir um funcionário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Funcionário excluído com sucesso (Sem Conteúdo)"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }
}