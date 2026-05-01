package geo.track.gestao.cliente.infraestructure.web;

import geo.track.externo.viacep.response.ResponseViacep;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPostEndereco;
import geo.track.gestao.cliente.infraestructure.request.endereco.RequestPutEndereco;
import geo.track.gestao.cliente.infraestructure.response.endereco.EnderecoResponse;
import geo.track.infraestructure.exception.ExceptionBody;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "Endereços", description = "Endpoints utilizados para gerenciar os endereços dos clientes")
@SecurityRequirement(name = "Bearer")
public interface EnderecoSwagger {

    @Operation(summary = "Criar endereço vazio para um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço vazio criado com sucesso", content = @Content(schema = @Schema(implementation = EnderecoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping("/registrar-vazio")
    ResponseEntity<EnderecoResponse> registrarEnderecoVazio(@PathVariable Integer idCliente);

    @Operation(summary = "Buscar endereço por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado", content = @Content(schema = @Schema(implementation = EnderecoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{idEndereco}")
    ResponseEntity<EnderecoResponse> getEnderecoById(@PathVariable Integer idCliente, @PathVariable Integer idEndereco);

    @Operation(summary = "Buscar endereço por CEP no ViaCEP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CEP encontrado", content = @Content(schema = @Schema(implementation = ResponseViacep.class))),
            @ApiResponse(responseCode = "404", description = "CEP inválido ou não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/viacep/{cep}")
    ResponseEntity<ResponseViacep> findEnderecoByVIACEP(@PathVariable String cep);

    @Operation(summary = "Cadastrar endereço para um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço cadastrado com sucesso", content = @Content(schema = @Schema(implementation = EnderecoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "409", description = "Endereço já existente", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = RequestPostEndereco.class),
                    examples = @ExampleObject(value = "{\"cep\":\"01414001\",\"logradouro\":\"Rua Haddock Lobo\",\"numero\":595,\"complemento\":\"Sala 12\",\"bairro\":\"Cerqueira César\",\"cidade\":\"São Paulo\",\"estado\":\"SP\",\"correspondencia\":true}")
            )
    )
    ResponseEntity<EnderecoResponse> postEndereco(@RequestBody @Valid RequestPostEndereco body, @PathVariable Integer idCliente);

    @Operation(summary = "Remover endereço de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço removido com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @DeleteMapping("/{idEndereco}")
    ResponseEntity<Void> deleteEndereco(@PathVariable Integer idCliente, @PathVariable Integer idEndereco);

    @Operation(summary = "Atualizar endereço de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso", content = @Content(schema = @Schema(implementation = EnderecoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PutMapping("/{idEndereco}")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = RequestPutEndereco.class),
                    examples = @ExampleObject(value = "{\"cep\":\"01414001\",\"logradouro\":\"Rua Haddock Lobo\",\"numero\":610,\"complemento\":\"Bloco B\",\"bairro\":\"Cerqueira César\",\"cidade\":\"São Paulo\",\"estado\":\"SP\",\"correspondencia\":false}")
            )
    )
    ResponseEntity<EnderecoResponse> putEndereco(@PathVariable Integer idCliente, @PathVariable Integer idEndereco, @RequestBody @Valid RequestPutEndereco body);
}
