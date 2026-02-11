package geo.track.controller;

import geo.track.config.GerenciadorTokenJwt;
import geo.track.controller.swagger.OrdemDeServicoSwagger;
import geo.track.domain.OrdemDeServico;
import geo.track.dto.os.request.*;
import geo.track.dto.os.response.OrdemDeServicoResponse;
import geo.track.mapper.OrdemDeServicoMapper;
import geo.track.service.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordens")
@RequiredArgsConstructor
public class OrdemDeServicoController implements OrdemDeServicoSwagger {
    private final OrdemDeServicoService ordemService;
    private final GerenciadorTokenJwt gerenciadorToken;

    @Override
    @PostMapping
    public ResponseEntity<OrdemDeServicoResponse> postOrdem(@RequestBody PostEntradaVeiculo ordemDTO){
        OrdemDeServico ordem = ordemService.postOrdem(ordemDTO);
        return ResponseEntity.status(201).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<OrdemDeServicoResponse>> findOrdem(){
        List<OrdemDeServico> ordem = ordemService.findOrdem() ;
        if (ordem.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @GetMapping("/{idOrdem}")
    public ResponseEntity<OrdemDeServicoResponse> findOrdemById(@PathVariable Integer idOrdem, @RequestHeader("Authorization") String token){
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;

        OrdemDeServico ordem = ordemService.findOrdemById(idOrdem);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PutMapping
    public ResponseEntity<OrdemDeServicoResponse> putValorESaida(@RequestBody RequestPutValorESaida ordemDTO){
        OrdemDeServico ordem = ordemService.putValorESaida(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/saidaEfetiva")
    public ResponseEntity<OrdemDeServicoResponse> patchSaidaEfetiva (@RequestBody RequestPatchSaidaEfetiva ordemDTO){
        OrdemDeServico ordem = ordemService.patchSaidaEfetiva(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/status")
    public ResponseEntity<OrdemDeServicoResponse> patchStatus (@RequestBody RequestPatchStatus ordemDTO){
        OrdemDeServico ordem = ordemService.patchStatus(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/seguradora")
    public ResponseEntity<OrdemDeServicoResponse> patchSeguradora (@RequestBody RequestPatchSeguradora ordemDTO){
        OrdemDeServico ordem = ordemService.patchSeguradora(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/nfRealizada")
    public ResponseEntity<OrdemDeServicoResponse> patchNfRealizada (@RequestBody RequestPatchNfRealizada ordemDTO){
        OrdemDeServico ordem = ordemService.patchNfRealizada(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/pagtoRealizado")
    public ResponseEntity<OrdemDeServicoResponse> patchPagtoRealizado (@RequestBody RequestPatchPagtoRealizado ordemDTO){
        OrdemDeServico ordem = ordemService.patchPagtoRealizado(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdem(@PathVariable Integer id){
        ordemService.deleteOrdem(id);
        return ResponseEntity.status(204).build();
    }
}