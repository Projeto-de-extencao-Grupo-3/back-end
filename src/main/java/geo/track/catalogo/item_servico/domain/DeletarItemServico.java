package geo.track.catalogo.item_servico.domain;

import geo.track.catalogo.item_servico.infraestructure.persistence.entity.ItemServico;
import geo.track.catalogo.item_servico.infraestructure.persistence.ItemServicoRepository;
import geo.track.catalogo.item_servico.application.DeletarItemServicoUseCase;
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
        ItemServico item = ITEM_SERVICO_SERVICE.buscarItemServicoPorId(id);
        ITEM_SERVICO_REPOSITORY.delete(item);
    }
}

