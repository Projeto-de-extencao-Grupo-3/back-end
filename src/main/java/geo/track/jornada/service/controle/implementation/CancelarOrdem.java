package geo.track.jornada.service.controle.implementation;

import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.enums.Status;
import geo.track.jornada.service.controle.CancelarOrdemUseCase;
import geo.track.jornada.service.ordemServico.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancelarOrdem implements CancelarOrdemUseCase {
    private final OrdemDeServicoService ordemDeServicoService;
    private final OrdemDeServicoRepository ordemDeServicoRepository;

    @Override
    public void execute(Integer idOrdem) {
        OrdemDeServico ordem = ordemDeServicoService.buscarOrdemServicoPorId(idOrdem);

        if (!Status.podeSerCancelada(ordem.getStatus())) {
            throw new BadBusinessRuleException("A ordem de serviço com status " + ordem.getStatus() + " não pode ser cancelada.", Domains.ORDEM_DE_SERVICO);
        }

        ordem.setStatus(Status.CANCELADO);
        ordem.setAtivo(false);
        ordemDeServicoRepository.save(ordem);
    }
}
