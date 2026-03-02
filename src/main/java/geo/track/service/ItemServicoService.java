package geo.track.service;

import geo.track.domain.ItemServico;
import geo.track.domain.OrdemDeServico;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.EnumDomains;
import geo.track.exception.constraint.message.GlobalExceptionMessages; // Assuming a generic ID_JA_EXISTE
import geo.track.exception.constraint.message.ItemServicoExceptionMessages;
import geo.track.repository.ItemServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServicoService {
    private final ItemServicoRepository ITEM_SERVICO_REPOSITORY;

    public ItemServico cadastrar(ItemServico itemServico){
        return ITEM_SERVICO_REPOSITORY.save(itemServico);
    }

    public List<ItemServico> listar(){
        return ITEM_SERVICO_REPOSITORY.findAll();
    }

    public ItemServico findById(Integer id){
        Optional<ItemServico> ordem = ITEM_SERVICO_REPOSITORY.findById(id);

        if (ordem.isEmpty()){
            throw new DataNotFoundException(ItemServicoExceptionMessages.ITEM_SERVICO_NAO_ENCONTRADO, EnumDomains.ITEM_SERVICO);
        }
        return ordem.get();
    }

    public List<ItemServico> listarPelaOrdemServico(OrdemDeServico ordemDeServicos) {
        List<ItemServico> servicos = ITEM_SERVICO_REPOSITORY.findAllByFkOrdemServicoIdOrdemServico(ordemDeServicos.getIdOrdemServico());

        return servicos;
    }

    public ItemServico atualizar(Integer id, ItemServico updatedItens) {
        ItemServico existente = ITEM_SERVICO_REPOSITORY.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ItemServicoExceptionMessages.ITEM_SERVICO_NAO_ENCONTRADO, EnumDomains.ITEM_SERVICO));

        existente.setPrecoCobrado(updatedItens.getPrecoCobrado());
        existente.setParteVeiculo(updatedItens.getParteVeiculo());
        existente.setLadoVeiculo(updatedItens.getLadoVeiculo());
        existente.setCor(updatedItens.getCor());
        existente.setEspecificacaoServico(updatedItens.getEspecificacaoServico());
        existente.setObservacoesItem(updatedItens.getObservacoesItem());

        existente.setTipoServico(updatedItens.getTipoServico());
        existente.setFkOrdemServico(updatedItens.getFkOrdemServico());

        return ITEM_SERVICO_REPOSITORY.save(existente);
    }

    public void delete(Integer id){
        ITEM_SERVICO_REPOSITORY.deleteById(id);
    }
}
