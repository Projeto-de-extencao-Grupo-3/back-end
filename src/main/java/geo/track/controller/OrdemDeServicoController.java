package geo.track.controller;

import geo.track.controller.swagger.OrdemDeServicoSwagger;
import geo.track.dto.os.request.*;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.dto.os.request.*;
import geo.track.dto.os.response.OrdemDeServicoResponse;
import geo.track.enums.os.StatusVeiculo;
import geo.track.exception.BadRequestException;
import geo.track.exception.ConflictException;
import geo.track.exception.constraint.message.Domains;
import geo.track.exception.constraint.message.OrdemDeServicoExceptionMessages;
import geo.track.mapper.OrdemDeServicoMapper;
import geo.track.service.OrdemDeServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ordens")
@RequiredArgsConstructor
public class OrdemDeServicoController implements OrdemDeServicoSwagger {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @Override
//    @PostMapping
//    Criação de Ordem de Servico é feito automaticamente na entrada
    public ResponseEntity<OrdemDeServicoResponse> postOrdem(@RequestBody RequestPostEntradaVeiculo ordemDTO) {
        if (ORDEM_SERVICO_SERVICE.existeOrdemServicoPorEntrada(ordemDTO.getFkEntrada())) {
            throw new ConflictException(OrdemDeServicoExceptionMessages.ORDEM_JA_EXISTE_PARA_REGISTRO_ENTRADA, Domains.ORDEM_DE_SERVICO);
        }

        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.cadastrarOrdemServico(ordemDTO);
        return ResponseEntity.status(201).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<OrdemDeServicoResponse>> listOrdens(@RequestParam(required = false) Integer intervalo, @AuthenticationPrincipal UsuarioDetalhesDto usuario) {
        Integer idOficina = usuario.getIdOficina();

        List<OrdemDeServico> ordem;

        if (intervalo != null) ordem = ORDEM_SERVICO_SERVICE.listarOrdensServicoIntervaloMeses(intervalo, idOficina);
        else ordem = ORDEM_SERVICO_SERVICE.listarOrdensServico(idOficina);

        if (ordem.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @GetMapping("/veiculos/{placa}")
    public ResponseEntity<List<OrdemDeServicoResponse>> findOrdemByPlaca(@PathVariable String placa, @AuthenticationPrincipal UsuarioDetalhesDto usuario) {
        Integer idOficina = usuario.getIdOficina();
        List<OrdemDeServico> ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorPlaca(placa, idOficina);
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
            throw new BadRequestException("Status inválido. Os permitidos são: %s".formatted(Arrays.toString(StatusVeiculo.values())), Domains.ORDEM_DE_SERVICO);
        }

        StatusVeiculo statusEnum = StatusVeiculo.valueOf(status.toUpperCase());
        List<OrdemDeServico> ordens;

        ordens = ORDEM_SERVICO_SERVICE.buscarOrdemPorStatus(statusEnum, idOficina);

        if (ordens.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordens));
    }

    @Override
    @GetMapping("/{idOrdem}")
    public ResponseEntity<OrdemDeServicoResponse> findOrdemById(@PathVariable Integer idOrdem, @AuthenticationPrincipal UsuarioDetalhesDto userAuthenticated) {
        Integer idOficina = userAuthenticated.getIdOficina();
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdem, idOficina);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PutMapping("/atualizar")
    public ResponseEntity<OrdemDeServicoResponse> putValorESaida(@RequestBody @Valid RequestPutValorESaida body) {
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.atualizarValorESaida(body);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/saida-efetiva")
    public ResponseEntity<OrdemDeServicoResponse> patchSaidaEfetiva(@RequestBody @Valid RequestPatchSaidaEfetiva body) {
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.atualizarSaidaEfetiva(body);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/status")
    public ResponseEntity<OrdemDeServicoResponse> patchStatus(@RequestBody @Valid RequestPatchStatus body) {
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.atualizarStatus(body);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/seguradora")
    public ResponseEntity<OrdemDeServicoResponse> patchSeguradora(@RequestBody @Valid RequestPatchSeguradora body) {
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.atualizarSeguradora(body);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/nf-realizada")
    public ResponseEntity<OrdemDeServicoResponse> patchNfRealizada(@RequestBody @Valid RequestPatchNfRealizada body) {
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.atualizarNotaFiscalRealizada(body);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @PatchMapping("/pagto-realizado")
    public ResponseEntity<OrdemDeServicoResponse> patchPagtoRealizado(@RequestBody @Valid RequestPatchPagtoRealizado body) {
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.atualizarPagamentoRealizado(body);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdem(@PathVariable Integer id) {
        ORDEM_SERVICO_SERVICE.deletarOrdemServico(id);
        return ResponseEntity.status(204).build();
    }
}