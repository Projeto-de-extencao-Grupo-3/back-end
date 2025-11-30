package geo.track.controller;

import geo.track.domain.Clientes;
import geo.track.dto.clientes.request.RequestPatchEmail;
import geo.track.dto.clientes.request.RequestPatchTelefone;
import geo.track.dto.clientes.request.RequestPostCliente;
import geo.track.dto.clientes.request.RequestPutCliente;
import geo.track.dto.clientes.response.ClienteResponse;
import geo.track.exception.ExceptionBody;
import geo.track.mapper.ClientesMapper;
import geo.track.service.ClientesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Endpoints utilizados para gerenciar os clientes")
@SecurityRequirement(name = "Bearer")
public class ClientesController {
    private final ClientesService clientesService;

    @GetMapping
    @Operation(summary = "Listar todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClienteResponse.class)))),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente cadastrado", content = @Content)
    })
    public ResponseEntity<List<ClienteResponse>> findAllClientes() {
        List<Clientes> clientes = clientesService.findClientes();

        if (clientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<ClienteResponse> response = ClientesMapper.toResponse(clientes);
        return ResponseEntity.status(200).body(response);
    }

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
    public ResponseEntity<ClienteResponse> postCliente(@Valid @RequestBody RequestPostCliente cliente) {
        Clientes novoCliente = clientesService.postCliente(cliente);
        return ResponseEntity.status(201).body(ClientesMapper.toResponse(novoCliente));
    }

    @Operation(summary = "Buscar cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso", content = {@Content(schema = @Schema(implementation = ClienteResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getClienteById(@PathVariable Integer id) {
        Clientes cliente = clientesService.findClienteById(id);

        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Operation(summary = "Buscar clientes pelo nome (parcial ou completo)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClienteResponse.class)))}),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado para o nome fornecido", content = @Content)
    })
    @GetMapping("/nome")
    public ResponseEntity<List<ClienteResponse>> getClienteByNome(@RequestParam String nome) {
        List<Clientes> clientes = clientesService.findClienteByNome(nome);
        if (clientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ClientesMapper.toResponse(clientes));
    }

    @Operation(summary = "Buscar cliente pelo CPF ou CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso", content = {@Content(schema = @Schema(implementation = ClienteResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado para o CPF/CNPJ", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @GetMapping("/cpfCnpj")
    public ResponseEntity<ClienteResponse> getClienteByCpfCnpj(@RequestParam String cpfCnpj) {
        Clientes cliente = clientesService.findClienteByCpfCnpj(cpfCnpj);
        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Operation(summary = "Atualizar o e-mail de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E-mail do cliente atualizado com sucesso", content = {@Content(schema = @Schema(implementation = ClienteResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Formato de e-mail inválido ou dados ausentes", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PatchMapping("/email")
    public ResponseEntity<ClienteResponse> patchEmailCliente(@RequestBody RequestPatchEmail clienteDTO) {
        Clientes cliente = clientesService.patchEmailCliente(clienteDTO);
        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Operation(summary = "Atualizar o telefone de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Telefone do cliente atualizado com sucesso", content = {@Content(schema = @Schema(implementation = ClienteResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Formato de telefone inválido ou dados ausentes", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PatchMapping("/telefone")
    public ResponseEntity<ClienteResponse> patchTelefoneCliente(@RequestBody RequestPatchTelefone clienteDTO) {
        Clientes cliente = clientesService.patchTelefoneCliente(clienteDTO);
        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Operation(summary = "Atualizar completamente um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = {@Content(schema = @Schema(implementation = ClienteResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para a atualização", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PutMapping()
    public ResponseEntity<ClienteResponse> putCliente(@RequestBody RequestPutCliente clienteDTO) {
        Clientes cliente = clientesService.putCliente(clienteDTO);
        return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
    }

    @Operation(summary = "Remover um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCliente(@PathVariable Integer id) {
        clientesService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}