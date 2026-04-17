package geo.track.jornada.domain.controle;

import geo.track.jornada.application.controle.DefinirNotaFiscalRealizadaUseCase;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.domain.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefinirNotaFiscalRealizada implements DefinirNotaFiscalRealizadaUseCase {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Override
    public OrdemDeServico execute(Integer idOrdemServico) {
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdemServico);
        ordem.setNfRealizada(true);

        return ORDEM_SERVICO_REPOSITORY.save(ordem);
    }
}
