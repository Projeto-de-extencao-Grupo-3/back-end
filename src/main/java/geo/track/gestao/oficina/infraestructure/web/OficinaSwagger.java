package geo.track.gestao.oficina.infraestructure.web;

import geo.track.gestao.oficina.infraestructure.request.OficinaPatchStatusDTO;
import geo.track.gestao.oficina.infraestructure.request.RequestPutOficina;
import geo.track.gestao.oficina.infraestructure.response.OficinaResponse;
import geo.track.infraestructure.auth.model.UsuarioCriacaoDto;
import geo.track.infraestructure.auth.model.UsuarioLoginDto;
import geo.track.infraestructure.auth.model.UsuarioTokenDto;
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
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Oficinas", description = "Endpoints para gerenciamento de oficinas")
public interface OficinaSwagger {
    @Operation(summary = "Cadastrar uma nova oficina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Oficina criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "409", description = "CNPJ já cadastrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = UsuarioCriacaoDto.class),
                    examples = @ExampleObject(value = "{\"razaoSocial\":\"Oficina Grotrack\",\"email\":\"contato@oficina.com\",\"cnpj\":\"68496284000192\",\"dt_criacao\":\"2026-04-27T10:00:00\",\"status\":true,\"senha\":\"123456\"}")
            )
    )
    ResponseEntity<Void> cadastrarEmpresa(@RequestBody @Valid UsuarioCriacaoDto empresa);

    @Operation(summary = "Autenticar oficina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticado com sucesso", content = @Content(schema = @Schema(implementation = UsuarioTokenDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "401", description = "Usuário ou senha inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = UsuarioLoginDto.class),
                    examples = @ExampleObject(value = "{\"email\":\"contato@oficina.com\",\"senha\":\"123456\"}")
            )
    )
    ResponseEntity<UsuarioTokenDto> login(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto);

    @Operation(summary = "Listar oficinas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oficinas listadas com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OficinaResponse.class))))
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/listar")
    ResponseEntity<List<OficinaResponse>> listarEmpresas();

    @Operation(summary = "Buscar oficina por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oficina encontrada", content = @Content(schema = @Schema(implementation = OficinaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    ResponseEntity<OficinaResponse> getEmpresaById(@PathVariable Integer id);

    @Operation(summary = "Buscar oficina por razão social")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oficinas retornadas com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OficinaResponse.class))))
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/razao-social")
    ResponseEntity<List<OficinaResponse>> getEmpresaByRazaoSocial(@RequestParam String razaoSocial);

    @Operation(summary = "Buscar oficina por CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oficina encontrada", content = @Content(schema = @Schema(implementation = OficinaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/cnpj")
    ResponseEntity<OficinaResponse> findEmpresaByCNPJ(@RequestParam String cnpj);

    @Operation(summary = "Atualizar oficina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oficina atualizada", content = @Content(schema = @Schema(implementation = OficinaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = RequestPutOficina.class),
                    examples = @ExampleObject(value = "{\"email\":\"novo-email@oficina.com\",\"status\":true}")
            )
    )
    ResponseEntity<OficinaResponse> atualizarEmpresa(@PathVariable Integer id, @RequestBody @Valid RequestPutOficina empresa);

    @Operation(summary = "Atualizar status da oficina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso", content = @Content(schema = @Schema(implementation = OficinaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/status")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = OficinaPatchStatusDTO.class),
                    examples = @ExampleObject(value = "{\"id\":1,\"status\":false}")
            )
    )
    ResponseEntity<OficinaResponse> patchStatus(@RequestBody @Valid OficinaPatchStatusDTO dto);

    @Operation(summary = "Remover oficina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Oficina removida com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> removerEmpresa(@PathVariable Integer id);
}
