package geo.track.controller;

import geo.track.domain.OrdemDeServicos;
import geo.track.dto.os.request.*;
import geo.track.exception.ExceptionBody;
import geo.track.service.OrdemDeServicosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/ordens")
@RequiredArgsConstructor
@Tag(name = "Ordem de Serviço", description = "Endpoints ultilizados para gerenciar as Ordens de Serviço e peças da oficina.")
@SecurityRequirement(name = "Bearer")
public class OrdemDeServicosController {
    private final OrdemDeServicosService ordemService;

    @Operation(summary = "Cadastrar nova Ordem de Serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ordem de Serviço cadastrada com sucesso", content = {
                    @Content(schema = @Schema(implementation = OrdemDeServicos.class))
            }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    public ResponseEntity<OrdemDeServicos> postOrdem(PostEntradaVeiculo ordemDTO){
        OrdemDeServicos ordem = ordemService.postOrdem(ordemDTO);
        return ResponseEntity.status(201).body(ordem);
    }

    @Operation(summary = "Listar todas as Ordens de Serviços")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Ordem de Serviço retornada com sucesso", content = {
                    @Content(schema = @Schema(implementation = OrdemDeServicos.class))
            }),
            @ApiResponse(responseCode = "204", description = "Nenhuma Ordem de Serviço encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping
    public ResponseEntity<List<OrdemDeServicos>> findOrdem(){
        List<OrdemDeServicos> ordem = ordemService.findOrdem();
        if (ordem.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ordem);
    }

    @Operation(summary = "Buscar Ordem de Serviço por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordem de Serviço encontrado com sucesso", content = {
                    @Content(schema = @Schema(implementation = OrdemDeServicos.class))
            }),
            @ApiResponse(responseCode = "404", description = "Ordem de Serviço não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{idOrdem}")
    public ResponseEntity<OrdemDeServicos> findOrdemById(Integer idOrdem){
        OrdemDeServicos ordem = ordemService.findOrdemById(idOrdem);
        return ResponseEntity.status(200).body(ordem);
    }

    @Operation(summary = "Atualizar valor e data de saída da ordem de serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordem de Serviço atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping
    public ResponseEntity<OrdemDeServicos> putValorESaida(RequestPutValorESaida ordemDTO){
        OrdemDeServicos ordem = ordemService.putValorESaida(ordemDTO);
        return ResponseEntity.status(200).body(ordem);
    }

    @Operation(summary = "Atualizar data de saída efetiva da Ordem de Serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saída efetiva atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/saidaEfetiva")
    public ResponseEntity<OrdemDeServicos> patchSaidaEfetiva (RequestPatchSaidaEfetiva ordemDTO){
        OrdemDeServicos ordem = ordemService.patchSaidaEfetiva(ordemDTO);
        return ResponseEntity.status(200).body(ordem);
    }

    @Operation(summary = "Atualizar status da ordem de serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/status")
    public ResponseEntity<OrdemDeServicos> patchStatus (RequestPatchStatus ordemDTO){
        OrdemDeServicos ordem = ordemService.patchStatus(ordemDTO);
        return ResponseEntity.status(200).body(ordem);
    }

    @Operation(summary = "Atualizar seguradora associada à Ordem de Serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguradora atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/seguradora")
    public ResponseEntity<OrdemDeServicos> patchSeguradora (RequestPatchSeguradora ordemDTO){
        OrdemDeServicos ordem = ordemService.patchSeguradora(ordemDTO);
        return ResponseEntity.status(200).body(ordem);
    }

    @Operation(summary = "Marcar nota fiscal como realizada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nota fiscal marcada como realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/nfRealizada")
    public ResponseEntity<OrdemDeServicos> patchNfRealizada (RequestPatchNfRealizada ordemDTO){
        OrdemDeServicos ordem = ordemService.patchNfRealizada(ordemDTO);
        return ResponseEntity.status(200).body(ordem);
    }

    @Operation(summary = "Marcar pagamento como realizado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento marcado como realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/pagtoRealizado")
    public ResponseEntity<OrdemDeServicos> patchPagtoRealizado (RequestPatchPagtoRealizado ordemDTO){
        OrdemDeServicos ordem = ordemService.patchPagtoRealizado(ordemDTO);
        return ResponseEntity.status(200).body(ordem);
    }

    @Operation(summary = "Excluir uma ordem de serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ordem de Serviço excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ordem de Serviço não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdem(@PathVariable Integer idOrdem){
        ordemService.deleteOrdem(idOrdem);
        return ResponseEntity.status(204).build();
    }
}
