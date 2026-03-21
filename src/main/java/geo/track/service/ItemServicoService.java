package geo.track.service;

import geo.track.annotation.ToRefactor;
import geo.track.config.DefaultMessages;
import geo.track.gestao.entity.ItemServico;
import geo.track.jornada.entity.OrdemDeServico;
import geo.track.dto.itensServicos.RequestPutItemServico;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.Domains;
import geo.track.exception.constraint.message.ItemServicoExceptionMessages;
import geo.track.jornada.entity.repository.OrdemDeServicoRepository;
import geo.track.jornada.request.itens.RequestPostItemServico;
import geo.track.log.Log;
import geo.track.mapper.ItemServicoMapper;
import geo.track.gestao.entity.repository.ItemServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServicoService {
    private final ItemServicoRepository ITEM_SERVICO_REPOSITORY;
    private final OrdemDeServicoRepository ORDEM_SERVICO_SERVICE;
    private final Log log;

    @ToRefactor(reason = DefaultMessages.refatoracaoDominioJornada)
    public ItemServico cadastrar(RequestPostItemServico body, Integer idOficina){
//        log.info("Iniciando cadastro de novo Item de Serviço para a Ordem de Serviço ID: {}", body.getFkOrdemServico());

//        OrdemDeServico ordemServico = ORDEM_SERVICO_SERVICE.findById(()).orElseThrow(() -> new DataNotFoundException(OrdemDeServicoExceptionMessages.ORDEM_NAO_ENCONTRADA_ID, Domains.ORDEM_DE_SERVICO));
//        ItemServico item = ItemServicoMapper.toEntity(body, ordemServico, body.getTipoServico());
//
//        return ITEM_SERVICO_REPOSITORY.save(item);
        return null;
    }

    public List<ItemServico> listar(){
        log.info("Listando todos os Itens de Serviço cadastrados.");
        return ITEM_SERVICO_REPOSITORY.findAll();
    }

    public ItemServico findById(Integer id){
        log.info("Buscando Item de Serviço pelo ID: {}", id);
        Optional<ItemServico> ordem = ITEM_SERVICO_REPOSITORY.findById(id);

        if (ordem.isEmpty()){
            log.warn("Item de Serviço com ID: {} não encontrado.", id);
            throw new DataNotFoundException(ItemServicoExceptionMessages.ITEM_SERVICO_NAO_ENCONTRADO, Domains.ITEM_SERVICO);
        }
        return ordem.get();
    }

    public List<ItemServico> listarPelaOrdemServico(OrdemDeServico body) {
        log.info("Listando Itens de Serviço vinculados à Ordem de Serviço ID: {}", body.getIdOrdemServico());
        List<ItemServico> servicos = ITEM_SERVICO_REPOSITORY.findAllByFkOrdemServicoIdOrdemServico(body.getIdOrdemServico());

        log.info("Total de itens encontrados para a OS {}: {}", body.getIdOrdemServico(), servicos.size());
        return servicos;
    }

    public ItemServico atualizar(Integer id, RequestPutItemServico body) {
        ItemServico existente = ITEM_SERVICO_REPOSITORY.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ItemServicoExceptionMessages.ITEM_SERVICO_NAO_ENCONTRADO, Domains.ITEM_SERVICO));

        log.info("Atualizando Item de Serviço ID: {}", id);
        
        ItemServicoMapper.updateDomain(existente, body);

        return ITEM_SERVICO_REPOSITORY.save(existente);
    }

    public void delete(Integer id){
        log.info("Removendo Item de Serviço ID: {}", id);
        ITEM_SERVICO_REPOSITORY.deleteById(id);
    }
}
