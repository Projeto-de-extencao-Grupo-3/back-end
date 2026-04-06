package geo.track.gestao.service.itemservico.implementations;

import geo.track.gestao.entity.ItemServico;
import geo.track.gestao.entity.repository.ItemServicoRepository;
import geo.track.gestao.service.ItemServicoService;
import geo.track.gestao.service.itemservico.DeletarItemServicoUseCase;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarItemServico implements DeletarItemServicoUseCase {
    private final ItemServicoService ITEM_SERVICO_SERVICE;
    private final ItemServicoRepository ITEM_SERVICO_REPOSITORY;
    private final Log log;

    public void execute(Integer id) {
        log.info("Removendo Item de Servico ID: {}", id);
        ItemServico item = ITEM_SERVICO_SERVICE.findById(id);
        ITEM_SERVICO_REPOSITORY.delete(item);
    }
}

