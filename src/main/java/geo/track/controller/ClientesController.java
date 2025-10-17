package geo.track.controller;

import geo.track.domain.Clientes;
import geo.track.domain.Oficinas;
import geo.track.dto.clientes.request.RequestPatchEmail;
import geo.track.dto.clientes.request.RequestPatchTelefone;
import geo.track.dto.clientes.request.RequestPutCliente;
import geo.track.dto.clientes.response.ResponseGetCliente;
import geo.track.exception.ExceptionBody;
import geo.track.service.ClientesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class ClientesController {
    private final ClientesService clientesService;

    @Operation(
            summary = "Cadastrar novo cliente",
            description = "Recebe um objeto e o armazena, retornando o cliente criado e seu devido ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente cadastrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Clientes.class))}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Cliente já existente com este CPF.",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
                // Adicionar aqui outras respostas de erro, como 400 (Bad Request) para falha na validação, se aplicável
    })
    @PostMapping
    public ResponseEntity<Clientes> postCliente(@Valid @RequestBody Clientes cliente) {
        Clientes clientes = clientesService.postCliente(cliente);
        return ResponseEntity.status(201).body(clientes);
    }

    @Operation(summary = "Buscar cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Clientes.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado",
                    // Assumindo que haja uma ExceptionBody para erros não encontrados, como na EnderecosController
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))} // Substituir Object.class por ExceptionBody.class se estiver disponível.
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseGetCliente> getClienteById(@PathVariable Integer id) {
        ResponseGetCliente cliente = clientesService.findClienteById(id);
        return ResponseEntity.status(200).body(cliente);
    }

    @Operation(summary = "Buscar clientes pelo nome (parcial ou completo)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Clientes encontrados com sucesso",
                    content = {@Content(schema = @Schema(implementation = List.class))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Nenhum cliente encontrado para o nome fornecido",
                    content = @Content
            )
    })
    @GetMapping("/nome")
    public ResponseEntity<List<Clientes>> getClienteByNome(@RequestParam String nome) {
        List<Clientes> cliente = clientesService.findClienteByNome(nome);
        if(cliente.isEmpty()){
            return ResponseEntity.status(204).body(cliente);
        }
        return ResponseEntity.status(200).body(cliente);
    }

    @Operation(summary = "Buscar cliente pelo CPF ou CNPJ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente encontrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Clientes.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado para o CPF/CNPJ",
                    // Assumindo que haja uma ExceptionBody para erros não encontrados.
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))} // Substituir Object.class por ExceptionBody.class se estiver disponível.
            )
            // Adicionar 400 (Bad Request) se a validação/formato do CPF/CNPJ puder falhar
    })
    @GetMapping("/cpfCnpj")
    public ResponseEntity<Clientes> getClienteByCpfCnpj(@RequestParam String cpfCnpj) {
        Clientes cliente = clientesService.findClienteByCpfCnpj(cpfCnpj);
        return ResponseEntity.status(200).body(cliente);
    }

    @Operation(
            summary = "Atualizar o e-mail de um cliente",
            description = "Recebe um objeto com o novo e-mail e o identificador e retornando o cliente atualizado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "E-mail do cliente atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Clientes.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado",
                    // Assumindo que haja uma ExceptionBody para erros não encontrados.
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))} // Substituir Object.class por ExceptionBody.class se estiver disponível.
            ),
            // Adicionar 400 (Bad Request) se a validação do e-mail puder falhar
    })
    @PatchMapping("/email")
    public ResponseEntity<Clientes> patchEmailCliente(@RequestBody RequestPatchEmail clienteDTO) {
        Clientes cliente = clientesService.patchEmailCliente(clienteDTO);
        return ResponseEntity.status(200).body(cliente);
    }

    @Operation(
            summary = "Atualizar o telefone de um cliente",
            description = "Recebe um objeto com o novo telefone e o identificador e retornando o cliente atualizado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Telefone do cliente atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Clientes.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado",
                    // Assumindo que haja uma ExceptionBody para erros não encontrados.
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))} // Substituir Object.class por ExceptionBody.class se estiver disponível.
            ),
            // Adicionar 400 (Bad Request) se a validação do telefone puder falhar
    })
    @PatchMapping("/telefone")
    public ResponseEntity<Clientes> patchTelefoneCliente(@RequestBody RequestPatchTelefone clienteDTO) {
        Clientes cliente = clientesService.patchTelefoneCliente(clienteDTO);
        return ResponseEntity.status(200).body(cliente);
    }

    @Operation(
            summary = "Atualizar completamente um cliente",
            description = "Recebe um objeto com os novos dados do cliente e o identificador e retornando o cliente atualizado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = Clientes.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado",
                    // Assumindo que haja uma ExceptionBody para erros não encontrados.
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))} // Substituir Object.class por ExceptionBody.class se estiver disponível.
            ),
            // Adicionar 400 (Bad Request) se a validação dos dados puder falhar
    })
    @PutMapping()
    public ResponseEntity<Clientes> putCliente(@RequestBody RequestPutCliente clienteDTO) {
        Clientes cliente = clientesService.putCliente(clienteDTO);
        return ResponseEntity.status(200).body(cliente);

    }

    @Operation(
            summary = "Remover um cliente",
            description = "Recebe o ID do cliente e o remove permanentemente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Cliente removido com sucesso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado",
                    // Assumindo que haja uma ExceptionBody para erros não encontrados.
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))} // Substituir Object.class por ExceptionBody.class se estiver disponível.
            )
            // Adicionar 400 (Bad Request) se o ID for inválido
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Clientes> removerCliente(@PathVariable Integer id){
         clientesService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}