package geo.track.gestao.service.itemservico.implementations;

import geo.track.gestao.entity.repository.ItemServicoRepository;
import geo.track.gestao.service.itemservico.DeletarItemServicoUseCase;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarItemServicoImplementation implements DeletarItemServicoUseCase {
    private final ItemServicoRepository ITEM_SERVICO_REPOSITORY;
    private final Log log;

    public void execute(Integer id) {
        log.info("Removendo Item de Servico ID: {}", id);
        ITEM_SERVICO_REPOSITORY.deleteById(id);
    }
}

