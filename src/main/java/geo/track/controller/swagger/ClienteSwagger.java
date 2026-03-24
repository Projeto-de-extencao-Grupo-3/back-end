package geo.track.controller.swagger;

import geo.track.dto.clientes.request.RequestPatchEmail;
import geo.track.dto.clientes.request.RequestPatchTelefone;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.dto.clientes.request.RequestPutCliente;
import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.infraestructure.exception.ExceptionBody;
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

@Tag(name = "Clientes", description = "Endpoints utilizados para gerenciar os clientes")
@SecurityRequirement(name = "Bearer")
public interface ClienteSwagger {

    @GetMapping
    @Operation(summary = "Listar clientes, podendo filtrar por nome ou CPF/CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClienteResponse.class)))),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado (quando filtrado por CPF/CNPJ ou Nome)", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    ResponseEntity<List<ClienteResponse>> findAllClientes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpfCnpj
    );

    @Operation(
            summary = "Cadastrar novo cliente",
            description = "Recebe um objeto e o armazena, retornando o cliente criado e seu devido ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso", content = {@Content(schema = @Schema(implementation = ClienteResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para o cliente", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "409", description = "Cliente já existente com este CPF.", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PostMapping
    ResponseEntity<ClienteResponse> postCliente(@Valid @RequestBody RequestPostCliente cliente);

    @Operation(summary = "Buscar cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso", content = {@Content(schema = @Schema(implementation = ClienteResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @GetMapping("/{id}")
    ResponseEntity<ClienteResponse> getClienteById(@PathVariable Integer id);

    @Operation(summary = "Atualizar o e-mail de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E-mail do cliente atualizado com sucesso", content = {@Content(schema = @Schema(implementation = ClienteResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Formato de e-mail inválido ou dados ausentes", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PatchMapping("/email")
    ResponseEntity<ClienteResponse> patchEmailCliente(@RequestBody RequestPatchEmail clienteDTO);

    @Operation(summary = "Atualizar o telefone de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Telefone do cliente atualizado com sucesso", content = {@Content(schema = @Schema(implementation = ClienteResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Formato de telefone inválido ou dados ausentes", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PatchMapping("/telefone")
    ResponseEntity<ClienteResponse> patchTelefoneCliente(@RequestBody RequestPatchTelefone clienteDTO);

    @Operation(summary = "Atualizar completamente um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = {@Content(schema = @Schema(implementation = ClienteResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para a atualização", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PutMapping()
    ResponseEntity<ClienteResponse> putCliente(@RequestBody RequestPutCliente clienteDTO);

    @Operation(summary = "Remover um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> removerCliente(@PathVariable Integer id);
}
