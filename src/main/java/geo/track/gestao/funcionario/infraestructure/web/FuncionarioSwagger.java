package geo.track.gestao.funcionario.infraestructure.web;

import geo.track.gestao.funcionario.infraestructure.request.RequestPostFuncionario;
import geo.track.gestao.funcionario.infraestructure.request.RequestPutFuncionario;
import geo.track.gestao.funcionario.infraestructure.response.FuncionarioResponse;
import geo.track.infraestructure.exception.ExceptionBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Funcionários", description = "Endpoints utilizados para gerenciar os funcionários")
@SecurityRequirement(name = "Bearer")
public interface FuncionarioSwagger {

    @Operation(summary = "Cadastrar um novo funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso", content = @Content(schema = @Schema(implementation = FuncionarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = RequestPostFuncionario.class),
                    examples = @ExampleObject(value = "{\"nome\":\"Carlos Souza\",\"cargo\":\"Mecânico\",\"especialidade\":\"Motor\",\"telefone\":\"11999990000\",\"senha\":\"123456\",\"email\":\"carlos@grotrack.com\",\"fkOficina\":1}")
            )
    )
    ResponseEntity<FuncionarioResponse> cadastrar(@RequestBody RequestPostFuncionario funcionario);

    @Operation(summary = "Listar funcionários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários listados com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FuncionarioResponse.class))))
    })
    @GetMapping()
    ResponseEntity<List<FuncionarioResponse>> listarFuncionarios();

    @Operation(summary = "Listar funcionários por nome com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum funcionário encontrado", content = @Content)
    })
    @GetMapping("/busca/nome")
    ResponseEntity<Page<FuncionarioResponse>> listarFuncionariosPorNomePaginado(
            @PageableDefault(size = 8, sort = "idFuncionario") Pageable pageable,
            @RequestParam(required = true) String nome
    );

    @Operation(summary = "Listar funcionários com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum funcionário encontrado", content = @Content)
    })
    @GetMapping("/funcionarios-paginados")
    ResponseEntity<Page<FuncionarioResponse>> listarFuncionariosPaginados(@PageableDefault(size = 8, sort = "idFuncionario") Pageable pageable);

    @Operation(summary = "Buscar funcionários por oficina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionários retornados com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FuncionarioResponse.class))))
    })
    @GetMapping("/oficina/{idOficina}")
    ResponseEntity<List<FuncionarioResponse>> findByOficina(@PathVariable Integer idOficina);

    @Operation(summary = "Buscar funcionário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado", content = @Content(schema = @Schema(implementation = FuncionarioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<FuncionarioResponse> buscarPorId(@PathVariable Integer id);

    @Operation(summary = "Atualizar funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso", content = @Content(schema = @Schema(implementation = FuncionarioResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PutMapping()
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = RequestPutFuncionario.class),
                    examples = @ExampleObject(value = "{\"id\":3,\"nome\":\"Carlos Souza\",\"cargo\":\"Líder de Oficina\",\"especialidade\":\"Motor\",\"telefone\":\"11988887777\",\"senha\":\"123456\",\"email\":\"carlos@grotrack.com\"}")
            )
    )
    ResponseEntity<FuncionarioResponse> atualizar(@RequestBody RequestPutFuncionario funcionario);

    @Operation(summary = "Excluir funcionário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Funcionário removido com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletar(@PathVariable Integer id);
}
