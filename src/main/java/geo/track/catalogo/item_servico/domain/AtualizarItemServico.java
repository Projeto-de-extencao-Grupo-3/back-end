package geo.track.catalogo.item_servico.domain;

import geo.track.catalogo.item_servico.infraestructure.persistence.entity.ItemServico;
import geo.track.catalogo.item_servico.infraestructure.persistence.ItemServicoRepository;
import geo.track.catalogo.item_servico.application.AtualizarItemServicoUseCase;
import geo.track.catalogo.item_servico.infraestructure.ItemServicoMapper;
import geo.track.catalogo.item_servico.infraestructure.request.RequestPutItemServico;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtualizarItemServico implements AtualizarItemServicoUseCase {
    private final ItemServicoRepository ITEM_SERVICO_REPOSITORY;
    private final ItemServicoService ITEM_SERVICO_SERVICE;
    private final Log log;

    public ItemServico execute(Integer id, RequestPutItemServico body) {
        ItemServico existente = ITEM_SERVICO_SERVICE.buscarItemServicoPorId(id);
        log.info("Atualizando Item de Servico ID: {}", id);

        ItemServicoMapper.updateDomain(existente, body);

        return ITEM_SERVICO_REPOSITORY.save(existente);
    }
}

