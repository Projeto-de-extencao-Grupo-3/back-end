package geo.track.controller.swagger;

import geo.track.controller.OficinaController;
import geo.track.dto.autenticacao.UsuarioCriacaoDto;
import geo.track.dto.autenticacao.UsuarioLoginDto;
import geo.track.dto.autenticacao.UsuarioTokenDto;
import geo.track.dto.oficinas.request.OficinaPatchEmailDTO;
import geo.track.dto.oficinas.request.OficinaPatchStatusDTO;
import geo.track.dto.oficinas.response.OficinaResponse;
import geo.track.domain.Oficinas;
import geo.track.exception.ExceptionBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Oficinas (Empresas)", description = "Endpoints utilizados para gerenciar Oficinas/Empresas.")
public interface OficinaSwagger {

    @Operation(summary = "Cadastrar uma nova Oficina/Empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Oficina cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de criação inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "409", description = "CNPJ/Email já cadastrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    ResponseEntity<Void> cadastrarEmpresa(@RequestBody @Valid UsuarioCriacaoDto empresa);

    @Operation(summary = "Realizar login e obter Token de acesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso", content = @Content(schema = @Schema(implementation = UsuarioTokenDto.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping("/login")
    ResponseEntity<UsuarioTokenDto> login(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto);

    @Operation(summary = "Listar todas as Oficinas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Oficinas retornada com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OficinaResponse.class)))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/listar")
    ResponseEntity<List<OficinaResponse>> listarEmpresas();

    @Operation(summary = "Buscar Oficina pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oficina encontrada com sucesso", content = @Content(schema = @Schema(implementation = OficinaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    ResponseEntity<OficinaResponse> getEmpresaById(@PathVariable Integer id);

    @Operation(summary = "Buscar Oficinas por Razão Social (busca parcial)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Oficinas encontrada com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OficinaResponse.class)))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/razao-social")
    ResponseEntity<List<OficinaResponse>> getEmpresaByRazaoSocial(@RequestParam String razaoSocial);

    @Operation(summary = "Buscar Oficina por CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oficina encontrada com sucesso", content = @Content(schema = @Schema(implementation = OficinaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/cnpj")
    ResponseEntity<OficinaResponse> findEmpresaByCNPJ(@RequestParam String cnpj);

    @Operation(summary = "Atualizar todos os dados de uma Oficina (exceto campos sensíveis)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Oficina atualizada com sucesso", content = @Content(schema = @Schema(implementation = OficinaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    ResponseEntity<OficinaResponse> atualizarEmpresa(@PathVariable Integer id, @RequestBody Oficinas empresa);

    @Operation(summary = "Atualizar o email de uma Oficina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email atualizado com sucesso", content = @Content(schema = @Schema(implementation = OficinaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (Email já em uso ou inválido)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/email")
    ResponseEntity<OficinaResponse> patchEmail(@RequestBody OficinaPatchEmailDTO dto);

    @Operation(summary = "Atualizar o status (ativo/inativo) de uma Oficina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso", content = @Content(schema = @Schema(implementation = OficinaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/status")
    ResponseEntity<OficinaResponse> patchStatus(@RequestBody OficinaPatchStatusDTO dto);

    @Operation(summary = "Remover uma Oficina pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Oficina removida com sucesso (Sem Conteúdo)"),
            @ApiResponse(responseCode = "404", description = "Oficina não encontrada", content = @Content(schema = @Schema(implementation = OficinaController.ExceptionBody.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content(schema = @Schema(implementation = OficinaController.ExceptionBody.class)))
    })
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> removerEmpresa(@PathVariable Integer id);
}
