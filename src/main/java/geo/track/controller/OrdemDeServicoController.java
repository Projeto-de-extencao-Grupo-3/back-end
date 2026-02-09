package geo.track.controller;

import geo.track.config.GerenciadorTokenJwt;
import geo.track.domain.OrdemDeServico;
import geo.track.dto.os.request.*;
import geo.track.dto.os.response.OrdemDeServicoResponse;
import geo.track.exception.ExceptionBody;
import geo.track.mapper.OrdemDeServicoMapper;
import geo.track.service.OrdemDeServicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@Tag(name = "Ordem de Serviço", description = "Endpoints utilizados para gerenciar as Ordens de Serviço.")
@SecurityRequirement(name = "Bearer")
public class OrdemDeServicoController {
    private final OrdemDeServicoService ordemService;
    private final GerenciadorTokenJwt gerenciadorToken;

    @Operation(summary = "Cadastrar nova Ordem de Serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ordem de Serviço cadastrada com sucesso", content = {
                    @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (dados incorretos)", content = @Content(schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @PostMapping
    public ResponseEntity<OrdemDeServicoResponse> postOrdem(@RequestBody PostEntradaVeiculo ordemDTO){
        OrdemDeServico ordem = ordemService.postOrdem(ordemDTO);
        return ResponseEntity.status(201).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Operation(summary = "Listar todas as Ordens de Serviços")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Ordens de Serviço retornada com sucesso", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = OrdemDeServicoResponse.class)))
            }),
            @ApiResponse(responseCode = "204", description = "Nenhuma Ordem de Serviço encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping
    public ResponseEntity<List<OrdemDeServicoResponse>> findOrdem(){
        List<OrdemDeServico> ordem = ordemService.findOrdem() ;
        if (ordem.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Operation(summary = "Buscar Ordem de Serviço por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordem de Serviço encontrada com sucesso", content = {
                    @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Ordem de Serviço não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ExceptionBody.class)))
    })
    @GetMapping("/{idOrdem}")
    public ResponseEntity<OrdemDeServicoResponse> findOrdemById(@PathVariable Integer idOrdem, @RequestHeader("Authorization") String token){
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        System.out.println("id do usuario que ta consultando:" + gerenciadorToken.getIdOficinaFromToken(jwtToken));

        OrdemDeServico ordem = ordemService.findOrdemById(idOrdem);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Operation(summary = "Atualizar valor e data de saída da ordem de serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordem de Serviço atualizada com sucesso", content = @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping
    public ResponseEntity<OrdemDeServicoResponse> putValorESaida(@RequestBody RequestPutValorESaida ordemDTO){
        OrdemDeServico ordem = ordemService.putValorESaida(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Operation(summary = "Atualizar data de saída efetiva da Ordem de Serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saída efetiva atualizada com sucesso", content = @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/saidaEfetiva")
    public ResponseEntity<OrdemDeServicoResponse> patchSaidaEfetiva (@RequestBody RequestPatchSaidaEfetiva ordemDTO){
        OrdemDeServico ordem = ordemService.patchSaidaEfetiva(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Operation(summary = "Atualizar status da ordem de serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso", content = @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/status")
    public ResponseEntity<OrdemDeServicoResponse> patchStatus (@RequestBody RequestPatchStatus ordemDTO){
        OrdemDeServico ordem = ordemService.patchStatus(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Operation(summary = "Atualizar seguradora associada à Ordem de Serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguradora atualizada com sucesso", content = @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/seguradora")
    public ResponseEntity<OrdemDeServicoResponse> patchSeguradora (@RequestBody RequestPatchSeguradora ordemDTO){
        OrdemDeServico ordem = ordemService.patchSeguradora(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Operation(summary = "Marcar nota fiscal como realizada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nota fiscal marcada como realizada com sucesso", content = @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/nfRealizada")
    public ResponseEntity<OrdemDeServicoResponse> patchNfRealizada (@RequestBody RequestPatchNfRealizada ordemDTO){
        OrdemDeServico ordem = ordemService.patchNfRealizada(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Operation(summary = "Marcar pagamento como realizado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento marcado como realizado com sucesso", content = @Content(schema = @Schema(implementation = OrdemDeServicoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping("/pagtoRealizado")
    public ResponseEntity<OrdemDeServicoResponse> patchPagtoRealizado (@RequestBody RequestPatchPagtoRealizado ordemDTO){
        OrdemDeServico ordem = ordemService.patchPagtoRealizado(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Operation(summary = "Excluir uma ordem de serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ordem de Serviço excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ordem de Serviço não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdem(@PathVariable Integer id){
        ordemService.deleteOrdem(id);
        return ResponseEntity.status(204).build();
    }
}