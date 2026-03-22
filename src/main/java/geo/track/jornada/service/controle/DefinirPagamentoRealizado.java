package geo.track.jornada.service.controle;

import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.service.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefinirPagamentoRealizado implements ControleJornadaStrategy<OrdemDeServico> {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final OrdemDeServicoRepository ORDEM_SERVICO_REPOSITORY;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.DEFINIR_PAGAMENTO_REALIZADO.equals(tipoJornada);
    }

    @Override
    public OrdemDeServico execute(Integer idOrdemServico, GetJornada requestGet) {
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdemServico);
        ordem.setPagtRealizado(true);

        return ORDEM_SERVICO_REPOSITORY.save(ordem);
    }
}
