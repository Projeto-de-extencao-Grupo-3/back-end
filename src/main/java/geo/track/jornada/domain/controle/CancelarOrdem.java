package geo.track.jornada.domain.controle;

import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.jornada.application.controle.CancelarOrdemUseCase;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.infraestructure.persistence.entity.Status;
import geo.track.jornada.domain.OrdemDeServicoService;
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
