package geo.track.controller.swagger;

import geo.track.dto.os.request.*;
import geo.track.dto.os.response.OrdemDeServicoResponse;
import geo.track.exception.ExceptionBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ordem de Serviço", description = "Endpoints utilizados para gerenciar as Ordens de Serviço.")
@SecurityRequirement(name = "Bearer")
public interface OrdemDeServicoSwagger {

    @Operation(summary = "Cadastrar nova Ordem de Serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ordem de Serviço cadastrada com sucesso", content = {
                    @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    ResponseEntity<OrdemDeServicoResponse> postOrdem(@RequestBody PostEntradaVeiculo ordemDTO);

    @Operation(summary = "Listar todas as Ordens de Serviços")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Ordens de Serviço retornada com sucesso", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = OrdemDeServicoResponse.class)))
            }),
            @ApiResponse(responseCode = "204", description = "Nenhuma Ordem de Serviço encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping
    ResponseEntity<List<OrdemDeServicoResponse>> findOrdem();

    @Operation(summary = "Buscar Ordem de Serviço por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordem de Serviço encontrada com sucesso", content = {
                    @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Ordem de Serviço não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{idOrdem}")
    ResponseEntity<OrdemDeServicoResponse> findOrdemById(@PathVariable Integer idOrdem, @RequestHeader("Authorization") String token);

    @Operation(summary = "Atualizar valor e data de saída da ordem de serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordem de Serviço atualizada com sucesso", content = @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping
    ResponseEntity<OrdemDeServicoResponse> putValorESaida(@RequestBody RequestPutValorESaida ordemDTO);

    @Operation(summary = "Atualizar data de saída efetiva da Ordem de Serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saída efetiva atualizada com sucesso", content = @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/saidaEfetiva")
    ResponseEntity<OrdemDeServicoResponse> patchSaidaEfetiva(@RequestBody RequestPatchSaidaEfetiva ordemDTO);

    @Operation(summary = "Atualizar status da ordem de serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso", content = @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/status")
    ResponseEntity<OrdemDeServicoResponse> patchStatus(@RequestBody RequestPatchStatus ordemDTO);

    @Operation(summary = "Atualizar seguradora associada à Ordem de Serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguradora atualizada com sucesso", content = @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/seguradora")
    ResponseEntity<OrdemDeServicoResponse> patchSeguradora(@RequestBody RequestPatchSeguradora ordemDTO);

    @Operation(summary = "Marcar nota fiscal como realizada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nota fiscal marcada como realizada com sucesso", content = @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/nfRealizada")
    ResponseEntity<OrdemDeServicoResponse> patchNfRealizada(@RequestBody RequestPatchNfRealizada ordemDTO);

    @Operation(summary = "Marcar pagamento como realizado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento marcado como realizado com sucesso", content = @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/pagtoRealizado")
    ResponseEntity<OrdemDeServicoResponse> patchPagtoRealizado(@RequestBody RequestPatchPagtoRealizado ordemDTO);

    @Operation(summary = "Excluir uma ordem de serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ordem de Serviço excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ordem de Serviço não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOrdem(@PathVariable Integer id);
}
