package geo.track.service;

import geo.track.domain.Funcionarios;
import geo.track.domain.ItensServicos;
import geo.track.domain.OrdemDeServicos;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.ItensServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItensServicosService {

    private final ItensServicoRepository itensServicoRepository;

    public ItensServicos cadastrar(ItensServicos itensServicos){
        if (itensServicoRepository.existsById(itensServicos.getIdItensServicos())){
            throw new ConflictException("ID já existe","Itens Serviço");
        }
        return itensServicoRepository.save(itensServicos);
    }

    public List<ItensServicos> listar(){
        return itensServicoRepository.findAll();
    }

    public ItensServicos findById(Integer id){
        Optional<ItensServicos> ordem = itensServicoRepository.findById(id);

        if (ordem.isEmpty()){
            throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Itens de Serviço");
        }
        return ordem.get();
    }

    public List<ItensServicos> listarPelaOrdemServico(OrdemDeServicos ordemDeServicos) {
        List<ItensServicos> servicos = itensServicoRepository.findAllByFkOrdemServicoIdOrdemServico(ordemDeServicos.getIdOrdemServico());

        return servicos;
    }

    public ItensServicos atualizar(Integer id, ItensServicos updatedItens) {
        ItensServicos existente = itensServicoRepository.findById(id)
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
