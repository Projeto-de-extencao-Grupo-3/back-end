package geo.track.gestao.cliente.infraestructure.web;

import geo.track.gestao.cliente.infraestructure.request.contato.RequestPostContato;
import geo.track.gestao.cliente.infraestructure.request.contato.RequestPutContato;
import geo.track.gestao.cliente.infraestructure.response.contato.ContatoResponse;
import geo.track.infraestructure.exception.ExceptionBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Contatos", description = "Endpoints utilizados para gerenciar contatos dos clientes")
@SecurityRequirement(name = "Bearer")
public interface ContatoSwagger {

    @Operation(summary = "Buscar contato pelo ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contato encontrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = ContatoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Contato não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @GetMapping("/{idContato}")
    ResponseEntity<ContatoResponse> getContatoById(
            @PathVariable Integer idCliente,
            @PathVariable Integer idContato
    );

    @Operation(
            summary = "Cadastrar novo contato",
            description = "Recebe os dados de um novo contato e o armazena, retornando o contato criado com seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Contato cadastrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = ContatoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para o contato",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PostMapping
    ResponseEntity<ContatoResponse> postContato(
            @PathVariable Integer idCliente,
            @RequestBody @Valid RequestPostContato body
    );

    @Operation(
            summary = "Atualizar contato",
            description = "Substitui os dados de um contato existente. O cliente e contato devem existir."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Contato atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = ContatoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente ou contato não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PutMapping("/{idContato}")
    ResponseEntity<ContatoResponse> putContato(
            @PathVariable Integer idCliente,
            @PathVariable Integer idContato,
            @RequestBody @Valid RequestPutContato body
    );

    @Operation(summary = "Remover contato")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Contato removido com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente ou contato não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @DeleteMapping("/{idContato}")
    ResponseEntity<Void> deleteContato(
            @PathVariable Integer idCliente,
            @PathVariable Integer idContato
    );
}

