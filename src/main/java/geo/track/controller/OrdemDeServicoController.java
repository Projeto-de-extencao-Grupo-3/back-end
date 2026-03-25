package geo.track.controller;

import geo.track.jornada.entity.OrdemDeServico;
import geo.track.dto.autenticacao.UsuarioDetalhesDto;
import geo.track.jornada.enums.Status;
import geo.track.jornada.response.listagem.OrdemDeServicoResponse;
import geo.track.infraestructure.exception.BadRequestException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.jornada.util.OrdemDeServicoMapper;
import geo.track.jornada.service.ordemServico.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ordens")
@RequiredArgsConstructor
public class OrdemDeServicoController {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<OrdemDeServicoResponse>> listOrdens(@PathVariable Integer idCliente, @RequestParam(required = false) Integer intervalo, @AuthenticationPrincipal UsuarioDetalhesDto usuario) {
        List<OrdemDeServico> ordem;

        if (intervalo != null) ordem = ORDEM_SERVICO_SERVICE.listarOrdensServicoIntervaloMeses(idCliente, intervalo);
        else ordem = ORDEM_SERVICO_SERVICE.listarOrdensServico();

        if (ordem.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @GetMapping("/veiculos/{placa}")
    public ResponseEntity<List<OrdemDeServicoResponse>> findOrdemByPlaca(@PathVariable String placa, @AuthenticationPrincipal UsuarioDetalhesDto usuario) {
        Integer idOficina = usuario.getIdOficina();
        List<OrdemDeServico> ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorPlaca(placa);
        if (ordem.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrdemDeServicoResponse>> findOrdemByStatus(@PathVariable String status, @AuthenticationPrincipal UsuarioDetalhesDto usuario) {
        Integer idOficina = usuario.getIdOficina();
        List<String> validStatus = Arrays.stream(Status.values()).map(Enum::name).toList();
        boolean isValid = validStatus.contains(status.toUpperCase());

        if (!isValid) {
            throw new BadRequestException("Status inválido. Os permitidos são: %s".formatted(Arrays.toString(Status.values())), Domains.ORDEM_DE_SERVICO);
        }

        Status statusEnum = Status.valueOf(status.toUpperCase());
        List<OrdemDeServico> ordens;

        ordens = ORDEM_SERVICO_SERVICE.buscarOrdemPorStatus(statusEnum);

        if (ordens.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordens));
    }

    @GetMapping("/{idOrdem}")
    public ResponseEntity<OrdemDeServicoResponse> findOrdemById(@PathVariable Integer idOrdem, @AuthenticationPrincipal UsuarioDetalhesDto userAuthenticated) {
        Integer idOficina = userAuthenticated.getIdOficina();
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdem);
        return ResponseEntity.status(200).body(OrdemDeServicoMapper.toResponse(ordem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdem(@PathVariable Integer id) {
        ORDEM_SERVICO_SERVICE.deletarOrdemServico(id);
        return ResponseEntity.status(204).build();
    }
}