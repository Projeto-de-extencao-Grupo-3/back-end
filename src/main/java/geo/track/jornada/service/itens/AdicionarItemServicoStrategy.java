package geo.track.jornada.service.itens;

import geo.track.gestao.entity.ItemServico;
import geo.track.gestao.service.itemservico.AdicionarItemServicoUseCase;
import geo.track.jornada.request.itens.RequestPostItemServico;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ADICIONAR SERVICO AO ORÇAMENTO **/
@Component
@RequiredArgsConstructor
public class AdicionarItemServicoStrategy implements ItensJornadaStrategy {
    private final AdicionarItemServicoUseCase ADICIONAR_ITEM_SERVICO_USE_CASE;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.ADICIONAR_ITEM_SERVICO.equals(tipoJornada);
    }

    @Override
    public ItemServico execute(Integer idOrdemServico, GetJornada getRequest) {
        RequestPostItemServico request = (RequestPostItemServico) getRequest;

        return ADICIONAR_ITEM_SERVICO_USE_CASE.execute(request, idOrdemServico);
    }
}
