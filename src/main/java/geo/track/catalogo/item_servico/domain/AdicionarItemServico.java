package geo.track.catalogo.item_servico.domain;

import geo.track.catalogo.item_servico.infraestructure.persistence.entity.ItemServico;
import geo.track.catalogo.item_servico.infraestructure.persistence.ItemServicoRepository;
import geo.track.catalogo.item_servico.application.AdicionarItemServicoUseCase;
import geo.track.catalogo.item_servico.infraestructure.ItemServicoMapper;
import geo.track.jornada.infraestructure.persistence.entity.OrdemDeServico;
import geo.track.catalogo.item_servico.infraestructure.request.RequestPostItemServico;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.domain.OrdemDeServicoService;
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



