package geo.track.controller.swagger;

import geo.track.domain.Oficina;
import geo.track.dto.autenticacao.UsuarioCriacaoDto;
import geo.track.dto.autenticacao.UsuarioLoginDto;
import geo.track.dto.autenticacao.UsuarioTokenDto;
import geo.track.dto.oficinas.request.OficinaPatchEmailDTO;
import geo.track.dto.oficinas.request.OficinaPatchStatusDTO;
import geo.track.dto.oficinas.request.RequestPutOficina;
import geo.track.dto.oficinas.response.OficinaResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Oficinas", description = "Endpoints para gerenciamento de Oficinas")
public interface OficinaSwagger {
    @Operation(summary = "Cadastrar uma nova Oficina")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Oficina criada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "CNPJ já cadastrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    ResponseEntity<Void> cadastrarEmpresa(@RequestBody @Valid UsuarioCriacaoDto empresa);

    @Operation(summary = "Realizar Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado"),
            @ApiResponse(responseCode = "401", description = "Acesso Negado")
    })
    ResponseEntity<UsuarioTokenDto> login(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto);

    @Operation(summary = "Listar todas as Oficinas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de Oficinas encontrada com sucesso",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = OficinaResponse.class)))}
            )
    })
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<List<OficinaResponse>> listarEmpresas();

    @Operation(summary = "Buscar Oficina pelo ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Oficina encontrada",
                    content = {@Content(schema = @Schema(implementation = OficinaResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Oficina não encontrada",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<OficinaResponse> getEmpresaById(@PathVariable Integer id);

    @Operation(summary = "Buscar Oficina por Razão Social")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Oficinas encontradas",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = OficinaResponse.class)))}
            )
    })
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<List<OficinaResponse>> getEmpresaByRazaoSocial(@PathVariable String razaoSocial);

    @Operation(summary = "Buscar Oficina por CNPJ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Oficina encontrada",
                    content = {@Content(schema = @Schema(implementation = OficinaResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Oficina não encontrada",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<OficinaResponse> findEmpresaByCNPJ(@PathVariable String cnpj);


    @Operation(summary = "Atualizar completamente uma Oficina")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Oficina atualizada",
                    content = {@Content(schema = @Schema(implementation = OficinaResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Oficina não encontrada",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<OficinaResponse> atualizarEmpresa(@PathVariable Integer id, @RequestBody @Valid RequestPutOficina empresa);

    @Operation(summary = "Atualizar o email de uma Oficina")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Email da Oficina atualizado",
                    content = {@Content(schema = @Schema(implementation = OficinaResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Oficina não encontrada",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<OficinaResponse> patchEmail(@RequestBody @Valid OficinaPatchEmailDTO dto);

    @Operation(summary = "Atualizar o status de uma Oficina")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Status da Oficina atualizado",
                    content = {@Content(schema = @Schema(implementation = OficinaResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Oficina não encontrada",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<OficinaResponse> patchStatus(@RequestBody @Valid OficinaPatchStatusDTO dto);

    @Operation(summary = "Remover uma Oficina")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Oficina removida"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Oficina não encontrada",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @SecurityRequirement(name = "Bearer")
    ResponseEntity<Void> removerEmpresa(@PathVariable Integer id);
}