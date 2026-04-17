package geo.track.jornada.domain.controle;

import geo.track.infraestructure.log.Log;
import geo.track.jornada.application.controle.DefinirDataSaidaPrevistaUseCase;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.jornada.infraestructure.persistence.OrdemDeServicoRepository;
import geo.track.jornada.domain.OrdemDeServicoService;
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
