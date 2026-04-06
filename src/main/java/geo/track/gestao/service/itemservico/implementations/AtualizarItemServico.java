package geo.track.gestao.service.itemservico.implementations;

import geo.track.gestao.entity.ItemServico;
import geo.track.gestao.entity.repository.ItemServicoRepository;
import geo.track.gestao.service.itemservico.AtualizarItemServicoUseCase;
import geo.track.gestao.util.ItemServicoMapper;
import geo.track.dto.itensServicos.RequestPutItemServico;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ItemServicoExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtualizarItemServico implements AtualizarItemServicoUseCase {
    private final ItemServicoRepository ITEM_SERVICO_REPOSITORY;
    private final Log log;

    public ItemServico execute(Integer id, RequestPutItemServico body) {
        ItemServico existente = ITEM_SERVICO_REPOSITORY.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ItemServicoExceptionMessages.ITEM_SERVICO_NAO_ENCONTRADO, Domains.ITEM_SERVICO));

        log.info("Atualizando Item de Servico ID: {}", id);

        ItemServicoMapper.updateDomain(existente, body);

        return ITEM_SERVICO_REPOSITORY.save(existente);
    }
}

