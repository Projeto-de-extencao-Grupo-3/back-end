package geo.track.gestao.veiculo.infraestructure.web;

import geo.track.gestao.veiculo.infraestructure.request.RequestPatchCor;
import geo.track.gestao.veiculo.infraestructure.request.RequestPatchPlaca;
import geo.track.gestao.veiculo.infraestructure.request.RequestPostVeiculo;
import geo.track.gestao.veiculo.infraestructure.request.RequestPutVeiculo;
import geo.track.gestao.veiculo.infraestructure.response.VeiculoResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Veículos", description = "Endpoints para gerenciamento de veículos na plataforma")
@SecurityRequirement(name = "Bearer")
public interface VeiculoSwagger {

    @Operation(summary = "Cadastrar um novo veículo",
            description = "Recebe os dados de um novo veículo, garantindo que a placa seja única e validando os formatos e tamanhos das propriedades antes de criá-lo.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Veículo criado com sucesso",
                    content = {@Content(schema = @Schema(implementation = VeiculoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para o veículo",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Veículo já existente com a placa informada",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = RequestPostVeiculo.class),
                    examples = @ExampleObject(value = "{\"placa\":\"BRA2E19\",\"marca\":\"Volkswagen\",\"modelo\":\"Voyage\",\"prefixo\":\"VTR-01\",\"anoModelo\":2022,\"idCliente\":1}")
            )
    )
    ResponseEntity<VeiculoResponse> cadastrar(@Valid @RequestBody RequestPostVeiculo body);

    @Operation(summary = "Listar todos os veículos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de veículos recuperada com sucesso",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = VeiculoResponse.class)))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Nenhum veículo encontrado",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    ResponseEntity<List<VeiculoResponse>> listar();

    @Operation(summary = "Buscar um veículo pelo ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo encontrado",
                    content = {@Content(schema = @Schema(implementation = VeiculoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado pelo ID informado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    ResponseEntity<VeiculoResponse> findVeiculoById(@PathVariable Integer id);

    @Operation(summary = "Buscar um veículo pela placa",
            description = "Retorna o veículo correspondente à placa exata informada. A busca ignora maiúsculas e minúsculas.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo encontrado",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = VeiculoResponse.class)))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Nenhum veículo encontrado para a placa informada",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    ResponseEntity<List<VeiculoResponse>> findVeiculoByPlaca(@PathVariable String placa);

    @Operation(summary = "Buscar veículos por ID do Cliente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Veículos encontrados para o cliente",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = VeiculoResponse.class)))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Nenhum veículo encontrado para o cliente informado",
                    content = {@Content(schema = @Schema(hidden = true))}
            )
    })
    ResponseEntity<List<VeiculoResponse>> findVeiculoByClienteId(@PathVariable Integer id);

    @Operation(summary = "Atualizar completamente os dados de um veículo",
            description = "Substitui as informações de um veículo existente. O ID deve ser válido e existir na base.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = VeiculoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para a atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = RequestPutVeiculo.class),
                    examples = @ExampleObject(value = "{\"marca\":\"Volkswagen\",\"modelo\":\"Voyage\",\"prefixo\":\"VTR-09\",\"anoModelo\":2023}")
            )
    )
    ResponseEntity<VeiculoResponse> putVeiculo(@PathVariable Integer id, @Valid @RequestBody RequestPutVeiculo veiculoAtt);

    @Operation(summary = "Atualizar a placa de um veículo")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Placa do veículo atualizada com sucesso",
                    content = {@Content(schema = @Schema(implementation = VeiculoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos (ex: formato de placa)",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Placa já utilizada por outro veículo",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = RequestPatchPlaca.class),
                    examples = @ExampleObject(value = "{\"idVeiculo\":1,\"placa\":\"ABC1D23\"}")
            )
    )
    ResponseEntity<VeiculoResponse> patchPlaca(@RequestBody @Valid RequestPatchPlaca veiculoAtt);

    @Operation(summary = "Atualizar a cor de um veículo")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cor do veículo atualizada com sucesso",
                    content = {@Content(schema = @Schema(implementation = VeiculoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    schema = @Schema(implementation = RequestPatchCor.class),
                    examples = @ExampleObject(value = "{\"idVeiculo\":1}")
            )
    )
    ResponseEntity<VeiculoResponse> patchCor(@RequestBody @Valid RequestPatchCor veiculoAtt);

    @Operation(summary = "Remover um veículo pelo ID",
            description = "Exclui um veículo da base de dados com o ID informado. Apenas exclui se existir.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Veículo removido com sucesso",
                    content = {@Content(schema = @Schema(hidden = true))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado pelo ID",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    ResponseEntity<Void> deleteVeiculoById(@PathVariable Integer id);
}
