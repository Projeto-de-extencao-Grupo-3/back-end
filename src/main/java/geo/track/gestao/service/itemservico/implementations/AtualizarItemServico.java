package geo.track.gestao.service.itemservico.implementations;

import geo.track.gestao.entity.ItemServico;
import geo.track.gestao.entity.repository.ItemServicoRepository;
import geo.track.gestao.service.ItemServicoService;
import geo.track.gestao.service.itemservico.AtualizarItemServicoUseCase;
import geo.track.gestao.util.ItemServicoMapper;
import geo.track.dto.itensServicos.RequestPutItemServico;
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

