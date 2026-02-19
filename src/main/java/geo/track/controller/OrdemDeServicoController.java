package geo.track.controller;

import geo.track.config.GerenciadorTokenJwt;
import geo.track.controller.swagger.OrdemDeServicoSwagger;
import geo.track.domain.OrdemDeServico;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.dto.os.request.*;
import geo.track.dto.os.response.*;
import geo.track.enums.os.StatusVeiculo;
import geo.track.exception.BadRequestException;
import geo.track.mapper.OrdemDeServicoMapper;
import geo.track.service.OrdemDeServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/ordens")
@RequiredArgsConstructor
public class OrdemDeServicoController implements OrdemDeServicoSwagger {
    private final OrdemDeServicoService ordemService;

    @Override
    @PostMapping
    public ResponseEntity<OrdemDeServicoResponse> postOrdem(@RequestBody PostEntradaVeiculo ordemDTO) {
        if (ordemService.existsOrdemByRegistroEntrada(ordemDTO.getFkEntrada())) {
            throw new BadRequestException("Já existe uma ordem de serviço para o registro de entrada com ID %d".formatted(ordemDTO.getFkEntrada()), "Ordem de Serviço");
        }

        OrdemDeServico ordem = ordemService.postOrdem(ordemDTO);
        return ResponseEntity.status(201).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<OrdemDeServicoResponse>> findOrdem(@AuthenticationPrincipal UsuarioDetalhesDto usuario) {
        Integer idOficina = usuario.getIdOficina();
        List<OrdemDeServico> ordem = ordemService.findOrdem(idOficina);
        if (ordem.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @GetMapping("/veiculos/{placa}")
    public ResponseEntity<List<OrdemDeServicoResponse>> findOrdemByPlaca(@PathVariable String placa, @AuthenticationPrincipal UsuarioDetalhesDto usuario) {
        Integer idOficina = usuario.getIdOficina();
        List<OrdemDeServico> ordem = ordemService.findOrdemByPlaca(placa, idOficina);
        if (ordem.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrdemDeServicoResponse>> findOrdemByStatus(@PathVariable String status, @AuthenticationPrincipal UsuarioDetalhesDto usuario) {
        Integer idOficina = usuario.getIdOficina();
        List<String> validStatus = Arrays.stream(StatusVeiculo.values()).map(Enum::name).toList();
        boolean isValid = validStatus.contains(status.toUpperCase());

        if (!isValid) {
            throw new BadRequestException("Status inválido. Os permitidos são: %s".formatted(Arrays.toString(StatusVeiculo.values())), "Ordem de Serviço");
        }

        StatusVeiculo statusEnum = StatusVeiculo.valueOf(status.toUpperCase());
        List<OrdemDeServico> ordens;

        // Busca dos ultimos 30 dias
        if (statusEnum.equals(StatusVeiculo.FINALIZADO)) {
            ordens = ordemService.findOrdemByStatusUltimos30Dias(idOficina);
        } else {
            ordens = ordemService.findOrdemByStatus(statusEnum, idOficina);
        }

        if (ordens.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordens));
    }

    @Override
    @GetMapping("/{idOrdem}")
    public ResponseEntity<OrdemDeServicoResponse> findOrdemById(@PathVariable Integer idOrdem, @AuthenticationPrincipal UsuarioDetalhesDto userAuthenticated) {
        Integer idOficina = userAuthenticated.getIdOficina();
        OrdemDeServico ordem = ordemService.findOrdemById(idOrdem, idOficina);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PutMapping
    public ResponseEntity<OrdemDeServicoResponse> putValorESaida(@RequestBody RequestPutValorESaida ordemDTO) {
        OrdemDeServico ordem = ordemService.putValorESaida(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/saidaEfetiva")
    public ResponseEntity<OrdemDeServicoResponse> patchSaidaEfetiva(@RequestBody RequestPatchSaidaEfetiva ordemDTO) {
        OrdemDeServico ordem = ordemService.patchSaidaEfetiva(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/status")
    public ResponseEntity<OrdemDeServicoResponse> patchStatus(@RequestBody RequestPatchStatus ordemDTO) {
        OrdemDeServico ordem = ordemService.patchStatus(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/seguradora")
    public ResponseEntity<OrdemDeServicoResponse> patchSeguradora(@RequestBody RequestPatchSeguradora ordemDTO) {
        OrdemDeServico ordem = ordemService.patchSeguradora(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/nfRealizada")
    public ResponseEntity<OrdemDeServicoResponse> patchNfRealizada(@RequestBody RequestPatchNfRealizada ordemDTO) {
        OrdemDeServico ordem = ordemService.patchNfRealizada(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/pagtoRealizado")
    public ResponseEntity<OrdemDeServicoResponse> patchPagtoRealizado(@RequestBody RequestPatchPagtoRealizado ordemDTO) {
        OrdemDeServico ordem = ordemService.patchPagtoRealizado(ordemDTO);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdem(@PathVariable Integer id) {
        ordemService.deleteOrdem(id);
        return ResponseEntity.status(204).build();
    }
}