package geo.track.controller;

import geo.track.domain.Clientes;
import geo.track.domain.OrdemDeServicos;
import geo.track.dto.os.request.*;
import geo.track.service.OrdemDeServicosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/ordens")
public class OrdemDeServicosController {
    private final OrdemDeServicosService ordemService;

    public OrdemDeServicosController(OrdemDeServicosService ordemService) {
        this.ordemService = ordemService;
    }

    @PostMapping
    public ResponseEntity<OrdemDeServicos> postOrdem(PostEntradaVeiculo ordemDTO){
        OrdemDeServicos ordem = ordemService.postOrdem(ordemDTO);
        return ResponseEntity.status(201).body(ordem);
    }


    @GetMapping
    public ResponseEntity<List<OrdemDeServicos>> findOrdem(){
        List<OrdemDeServicos> ordem = ordemService.findOrdem();
        if (ordem.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ordem);
    }

    @GetMapping("/{idOrdem}")
    public ResponseEntity<OrdemDeServicos> findOrdemById(Integer idOrdem){
        OrdemDeServicos ordem = ordemService.findOrdemById(idOrdem);
        return ResponseEntity.status(200).body(ordem);
    }

    @PutMapping
    public ResponseEntity<OrdemDeServicos> putValorESaida(RequestPutValorESaida ordemDTO){
        OrdemDeServicos ordem = ordemService.putValorESaida(ordemDTO);
        return ResponseEntity.status(200).body(ordem);
    }

    @PatchMapping("/saidaEfetiva")
    public ResponseEntity<OrdemDeServicos> patchSaidaEfetiva (RequestPatchSaidaEfetiva ordemDTO){
        OrdemDeServicos ordem = ordemService.patchSaidaEfetiva(ordemDTO);
        return ResponseEntity.status(200).body(ordem);
    }

    @PatchMapping("/status")
    public ResponseEntity<OrdemDeServicos> patchStatus (RequestPatchStatus ordemDTO){
        OrdemDeServicos ordem = ordemService.patchStatus(ordemDTO);
        return ResponseEntity.status(200).body(ordem);
    }

    @PatchMapping("/seguradora")
    public ResponseEntity<OrdemDeServicos> patchSeguradora (RequestPatchSeguradora ordemDTO){
        OrdemDeServicos ordem = ordemService.patchSeguradora(ordemDTO);
        return ResponseEntity.status(200).body(ordem);
    }

    @PatchMapping("/nfRealizada")
    public ResponseEntity<OrdemDeServicos> patchNfRealizada (RequestPatchNfRealizada ordemDTO){
        OrdemDeServicos ordem = ordemService.patchNfRealizada(ordemDTO);
        return ResponseEntity.status(200).body(ordem);
    }

    @PatchMapping("/pagtoRealizado")
    public ResponseEntity<OrdemDeServicos> patchPagtoRealizado (RequestPatchPagtoRealizado ordemDTO){
        OrdemDeServicos ordem = ordemService.patchPagtoRealizado(ordemDTO);
        return ResponseEntity.status(200).body(ordem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdem(@PathVariable Integer idOrdem){
        ordemService.deleteOrdem(idOrdem);
        return ResponseEntity.status(204).build();
    }
}
