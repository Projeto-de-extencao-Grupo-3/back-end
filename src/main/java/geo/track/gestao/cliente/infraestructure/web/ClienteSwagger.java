package geo.track.gestao.cliente.infraestructure.web;

import geo.track.gestao.cliente.infraestructure.response.cliente.ClienteVeiculoResponse;
import geo.track.gestao.cliente.infraestructure.response.contato.ContatoResponse;
import geo.track.gestao.cliente.infraestructure.request.cliente.RequestPostCliente;
import geo.track.gestao.cliente.infraestructure.request.cliente.RequestPutCliente;
import geo.track.gestao.cliente.infraestructure.response.cliente.ClienteResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    ResponseEntity<List<ClienteResponse>> findAllClientes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpfCnpj
    );

    @Operation(summary = "Listar clientes por nome com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de clientes retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @GetMapping("/busca/nome")
    ResponseEntity<Page<ClienteResponse>> findAllClientesPorNomePaginado(
            @PageableDefault(size = 8, sort = "idCliente") Pageable pageable,
            @RequestParam(required = true) String nome
    );

    @Operation(summary = "Listar todos os clientes com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de clientes retornada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autenticado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @GetMapping("/clientes-paginados")
    ResponseEntity<Page<ClienteResponse>> listarTodos(@PageableDefault(size = 8, sort = "idCliente") Pageable pageable);

    @Operation(
            summary = "Cadastrar novo cliente",
            description = "Recebe um objeto e o armazena, retornando o cliente criado e seu devido ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso", content = {@Content(schema = @Schema(implementation = ClienteResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para o cliente", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "404", description = "Oficina informada não encontrada", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "409", description = "Cliente já existente com este CPF", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = RequestPostCliente.class),
                    examples = @ExampleObject(value = "{\"nome\":\"João da Silva\",\"cpfCnpj\":\"12345678909\",\"inscricaoEstadual\":\"\",\"tipoCliente\":\"PESSOA_FISICA\",\"fkOficina\":1,\"contatos\":[{\"telefone\":\"11999990000\",\"email\":\"joao@email.com\",\"nomeContato\":\"João\",\"departamentoContato\":\"Compras\"}],\"endereco\":{\"cep\":\"01414001\",\"logradouro\":\"Rua Haddock Lobo\",\"numero\":595,\"complemento\":\"Sala 12\",\"bairro\":\"Cerqueira César\",\"cidade\":\"São Paulo\",\"estado\":\"SP\",\"correspondencia\":true}}")
            )
    )
    ResponseEntity<ClienteResponse> postCliente(@Valid @RequestBody RequestPostCliente cliente);

    @Operation(summary = "Buscar cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso", content = {@Content(schema = @Schema(implementation = ClienteResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @GetMapping("/{id}")
    ResponseEntity<ClienteResponse> getClienteById(@PathVariable Integer id);

    @Operation(summary = "Atualizar completamente um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = {@Content(schema = @Schema(implementation = ClienteResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para a atualização", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @PutMapping()
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = RequestPutCliente.class),
                    examples = @ExampleObject(value = "{\"idCliente\":1,\"nome\":\"João da Silva\",\"cpfCnpj\":\"12345678909\",\"inscricaoEstadual\":\"\"}")
            )
    )
    ResponseEntity<ClienteResponse> putCliente(@RequestBody RequestPutCliente clienteDTO);

    @Operation(summary = "Remover um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "422", description = "Cliente possui ordens de serviço abertas", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> removerCliente(@PathVariable Integer id);

    @Operation(summary = "Buscar cliente por placa do veículo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado pela placa", content = {@Content(schema = @Schema(implementation = ClienteVeiculoResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado para a placa informada", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @GetMapping("/placa/{placa}")
    ResponseEntity<ClienteVeiculoResponse> buscarClientePorPlaca(@PathVariable String placa);

    @Operation(summary = "Listar contatos de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contatos retornados com sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContatoResponse.class)))),
            @ApiResponse(responseCode = "204", description = "Cliente sem contatos cadastrados", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(schema = @Schema(implementation = ExceptionBody.class))})
    })
    @GetMapping("/{idCliente}/contatos")
    ResponseEntity<List<ContatoResponse>> listarContatosPorCliente(@PathVariable Integer idCliente);
}
