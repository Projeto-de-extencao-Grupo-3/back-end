package geo.track.jornada.service.controle.implementation;

import geo.track.infraestructure.log.Log;
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
    private final Log log;

    @Override
    public OrdemDeServico execute(Integer idOrdemServico, LocalDate dataSaidaPrevista) {
        log.info("Definindo data de saída prevista para a Ordem de Serviço ID: {} com data: {}", idOrdemServico, dataSaidaPrevista);

        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdemServico);
        ordem.setDataSaidaPrevista(dataSaidaPrevista);

        return ORDEM_SERVICO_REPOSITORY.save(ordem);
    }
}
