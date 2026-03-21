package geo.track.jornada.service.itens;

import geo.track.gestao.entity.ItemServico;
import geo.track.jornada.request.itens.RequestPostItemServico;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.enums.TipoJornada;
import geo.track.jornada.interfaces.GetJornada;
import geo.track.mapper.ItemServicoMapper;
import geo.track.gestao.entity.repository.ItemServicoRepository;
import geo.track.service.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Strategy para ADICIONAR SERVICO AO ORÇAMENTO **/
@Component
@RequiredArgsConstructor
public class AdicionarItemServicoStrategy implements ItensJornadaStrategy<ItemServico> {
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final ItemServicoRepository ITEM_SERVICO_REPOSITORY;

    @Override
    public Boolean isApplicable(TipoJornada tipoJornada) {
        return TipoJornada.ADICIONAR_ITEM_SERVICO.equals(tipoJornada);
    }

    @Override
    public ItemServico execute(Integer idOrdemServico, GetJornada getRequest) {
        RequestPostItemServico request = (RequestPostItemServico) getRequest;
        OrdemDeServico ordemDeServico = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdemServico);

        ItemServico itemServico = ItemServicoMapper.toEntity(request, ordemDeServico);

        return ITEM_SERVICO_REPOSITORY.save(itemServico);
    }
}
