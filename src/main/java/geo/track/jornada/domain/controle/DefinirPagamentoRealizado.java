package geo.track.jornada.domain.controle;

import geo.track.jornada.application.controle.DefinirPagamentoRealizadoUseCase;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.domain.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefinirPagamentoRealizado implements DefinirPagamentoRealizadoUseCase {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Override
    public OrdemDeServico execute(Integer idOrdemServico) {
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdemServico);
        ordem.setPagtRealizado(true);

        return ORDEM_SERVICO_REPOSITORY.save(ordem);
    }
}
