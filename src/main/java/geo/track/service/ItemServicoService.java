package geo.track.service;

import geo.track.gestao.entity.ItemServico;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.dto.itensServicos.RequestPutItemServico;
import geo.track.gestao.service.itemservico.AtualizarItemServicoUseCase;
import geo.track.gestao.service.itemservico.AdicionarItemServicoUseCase;
import geo.track.gestao.service.itemservico.DeletarItemServicoUseCase;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.ItemServicoExceptionMessages;
import geo.track.jornada.request.itens.RequestPostItemServico;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.entity.repository.ItemServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServicoService {
    private final ItemServicoRepository ITEM_SERVICO_REPOSITORY;
    private final Log log;

    public List<ItemServico> listar() {
        log.info("Listando todos os Itens de Servico cadastrados.");
        return ITEM_SERVICO_REPOSITORY.findAll();
    }

    public ItemServico findById(Integer id) {
        log.info("Buscando Item de Servico pelo ID: {}", id);
        Optional<ItemServico> ordem = ITEM_SERVICO_REPOSITORY.findById(id);

        if (ordem.isEmpty()) {
            log.warn("Item de Servico com ID: {} nao encontrado.", id);
            throw new DataNotFoundException(ItemServicoExceptionMessages.ITEM_SERVICO_NAO_ENCONTRADO, Domains.ITEM_SERVICO);
        }
        return ordem.get();
    }

    public List<ItemServico> listarPelaOrdemServico(OrdemDeServico body) {
        log.info("Listando Itens de Servico vinculados a Ordem de Servico ID: {}", body.getIdOrdemServico());
        List<ItemServico> servicos = ITEM_SERVICO_REPOSITORY.findAllByFkOrdemServicoIdOrdemServico(body.getIdOrdemServico());

        log.info("Total de itens encontrados para a OS {}: {}", body.getIdOrdemServico(), servicos.size());
        return servicos;
    }

}
