package geo.track.jornada.service.controle.implementation;

import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.service.controle.DefinirPagamentoRealizadoUseCase;
import geo.track.jornada.service.ordemServico.OrdemDeServicoService;
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
