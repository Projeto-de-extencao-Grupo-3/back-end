package geo.track.gestao.service.itemservico.implementations;

import geo.track.gestao.entity.ItemServico;
import geo.track.gestao.entity.repository.ItemServicoRepository;
import geo.track.gestao.service.itemservico.AdicionarItemServicoUseCase;
import geo.track.gestao.util.ItemServicoMapper;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.jornada.request.itens.RequestPostItemServico;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.service.ordemServico.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdicionarItemServico implements AdicionarItemServicoUseCase {
    private final ItemServicoRepository ITEM_SERVICO_REPOSITORY;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final Log log;

    public ItemServico execute(Integer idOrdemServico, RequestPostItemServico body) {
        log.info("Iniciando cadastro de novo Item de Servico");
        OrdemDeServico ordem = ORDEM_SERVICO_SERVICE.buscarOrdemServicoPorId(idOrdemServico);

        ItemServico item = ItemServicoMapper.toEntity(body, ordem);

        return ITEM_SERVICO_REPOSITORY.save(item);
    }
}



