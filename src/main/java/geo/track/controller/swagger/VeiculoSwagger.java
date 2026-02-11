package geo.track.controller.swagger;

import geo.track.domain.Veiculo;
import geo.track.dto.veiculos.request.RequestPatchCor;
import geo.track.dto.veiculos.request.RequestPatchPlaca;
import geo.track.dto.veiculos.response.VeiculoResponse;
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

@Tag(name = "Veículos", description = "Endpoints utilizados para gerenciar os veículos")
@SecurityRequirement(name = "Bearer")
public interface VeiculoSwagger {

    @Operation(
            summary = "Cadastrar novo veículo",
            description = "Recebe um objeto de veículo e o armazena, retornando o veículo criado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Veículo cadastrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = VeiculoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos fornecidos para o veículo",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Veículo com esta placa já cadastrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PostMapping
    ResponseEntity<VeiculoResponse> cadastrar(@Valid @RequestBody Veiculo veiculo);

    @Operation(summary = "Listar todos os veículos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de veículos encontrada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VeiculoResponse.class)))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Nenhum veículo cadastrado",
                    content = @Content
            )
    })
    @GetMapping
    ResponseEntity<List<VeiculoResponse>> listar();

    @Operation(summary = "Buscar veículo pelo ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo encontrado com sucesso",
                    content = {@Content(schema = @Schema(implementation = VeiculoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado",
                    content = @Content(schema = @Schema(implementation = ExceptionBody.class))
            )
    })
    @GetMapping("/{id}")
    ResponseEntity<VeiculoResponse> findVeiculoById(@PathVariable Integer id);

    @Operation(summary = "Buscar veículo(s) pela placa")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Veículo(s) encontrado(s) com sucesso",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VeiculoResponse.class)))}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Nenhum veículo encontrado para esta placa",
                    content = @Content
            )
    })
    @GetMapping("/placa/{placa}")
    ResponseEntity<List<VeiculoResponse>> findVeiculoByPlaca(@PathVariable String placa);

    @Operation(summary = "Atualizar completamente um veículo")
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
                    description = "Veículo não encontrado para atualização",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PutMapping("/{id}")
    ResponseEntity<VeiculoResponse> putVeiculo(@PathVariable Integer id, @RequestBody Veiculo veiculoAtt);

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
            )
    })
    @PatchMapping("/placa")
    ResponseEntity<VeiculoResponse> patchPlaca(@RequestBody RequestPatchPlaca veiculoDTO);

    @Operation(summary = "Atualizar a cor de um veículo")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cor do veículo atualizada com sucesso",
                    content = {@Content(schema = @Schema(implementation = VeiculoResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos (ex: cor em branco)",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Veículo não encontrado",
                    content = {@Content(schema = @Schema(implementation = ExceptionBody.class))}
            )
    })
    @PatchMapping("/cor")
    ResponseEntity<VeiculoResponse> patchCor(@RequestBody RequestPatchCor veiculoDTO);
}
