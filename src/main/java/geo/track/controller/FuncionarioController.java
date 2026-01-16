package geo.track.controller;

import geo.track.domain.Funcionario;
import geo.track.dto.funcionarios.FuncionarioResponse;
import geo.track.exception.ExceptionBody;
import geo.track.mapper.FuncionarioMapper;
import geo.track.service.FuncionarioService;
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
@RequiredArgsConstructor
@RequestMapping(path = "/funcionarios")
@Tag(name = "Funcionários", description = "Endpoints utilizados para gerenciar os funcionários.")
@SecurityRequirement(name = "Bearer")
public class FuncionarioController {

    private final FuncionarioService service;

    @Operation(summary = "Cadastrar um novo funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = FuncionarioResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    public ResponseEntity<FuncionarioResponse> cadastrar(@RequestBody Funcionario funcionario){
        Funcionario funcionarioResposta = service.cadastrar(funcionario);
        return ResponseEntity.status(201).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Operation(summary = "Buscar o funcionário pela fkOficina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = FuncionarioResponse.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/oficina/{idOficina}")
    public ResponseEntity<List<FuncionarioResponse>> findByOficina(@PathVariable Integer idOficina) {
        List<Funcionario> funcionarioResposta = service.buscarPorOficina(idOficina);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Operation(summary = "Buscar o funcionário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = FuncionarioResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> buscarPorId(@PathVariable Integer id){
        Funcionario funcionarioResposta = service.buscarPorId(id);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionarioResposta));
    }

    @Operation(summary = "Atualizar os dados de um funcionário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso", content = {
                    @Content(schema = @Schema(implementation = FuncionarioResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> atualizar(@PathVariable Integer id, @RequestBody Funcionario funcionario){
        Funcionario funcionarioResposta = service.atualizar(id, funcionario);
        return ResponseEntity.status(200).body(FuncionarioMapper.toResponse(funcionarioResposta));
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