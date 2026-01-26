package geo.track.service;

import geo.track.domain.ItemServico;
import geo.track.domain.OrdemDeServico;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.ItemServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServicoService {

    private final ItemServicoRepository itensServicoRepository;

    public ItemServico cadastrar(ItemServico itemServico){
        if (itensServicoRepository.existsById(itemServico.getIdRegistroServico())){
            throw new ConflictException("ID já existe","Itens Serviço");
        }
        return itensServicoRepository.save(itemServico);
    }

    public List<ItemServico> listar(){
        return itensServicoRepository.findAll();
    }

    public ItemServico findById(Integer id){
        Optional<ItemServico> ordem = itensServicoRepository.findById(id);

        if (ordem.isEmpty()){
            throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Itens de Serviço");
        }
        return ordem.get();
    }

    public List<ItemServico> listarPelaOrdemServico(OrdemDeServico ordemDeServicos) {
        List<ItemServico> servicos = itensServicoRepository.findAllByFkOrdemServicoIdOrdemServico(ordemDeServicos.getIdOrdemServico());

        return servicos;
    }

    public ItemServico atualizar(Integer id, ItemServico updatedItens) {
        ItemServico existente = itensServicoRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Item de serviço não encontrado", "ItensServiço"));

        existente.setPrecoCobrado(updatedItens.getPrecoCobrado());
        existente.setParteVeiculo(updatedItens.getParteVeiculo());
        existente.setLadoVeiculo(updatedItens.getLadoVeiculo());
        existente.setCor(updatedItens.getCor());
        existente.setEspecificacaoServico(updatedItens.getEspecificacaoServico());
        existente.setObservacoesItem(updatedItens.getObservacoesItem());

        existente.setFkServico(updatedItens.getFkServico());
        existente.setFkOrdemServico(updatedItens.getFkOrdemServico());

        return itensServicoRepository.save(existente);
    }

    public void delete(Integer id){
        itensServicoRepository.deleteById(id);
    }
}
