package geo.track.jornada.service.controle.implementation;

import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.request.controle.RequestPatchSaidaPrevista;
import geo.track.jornada.service.controle.DefinirDataSaidaPrevistaUseCase;
import geo.track.jornada.service.ordemServico.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DefinirDataSaidaPrevista implements DefinirDataSaidaPrevistaUseCase {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Override
    public OrdemDeServico execute(Integer idOrdemServico, LocalDate dataSaidaPrevista) {
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdemServico);
        ordem.setDataSaidaPrevista(dataSaidaPrevista);

        return ORDEM_SERVICO_REPOSITORY.save(ordem);
    }
}
