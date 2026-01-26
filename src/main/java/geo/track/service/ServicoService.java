package geo.track.service;

import geo.track.domain.Servico;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository repository;

    public Servico cadastrar(Servico servicoss){
        if (repository.existsByTituloServico(servicoss.getTituloServico())){
            throw new ConflictException("Nome já existe","Servico");
        }
        Servico servicoRegistrado = repository.save(servicoss);
        return servicoRegistrado;
    }

    public Servico buscarPorId(Integer id){
        if (!repository.existsByIdServico(id)){
            throw new DataNotFoundException("Servico Não encontrado","Servico");
        }
        Servico getServico = repository.getByIdServico(id);
        return getServico;
    }

    public Servico atualizar(Integer id, Servico servico){
        servico.setIdServico(id);
        if (!repository.existsById(id)){
            throw new DataNotFoundException("Servico não encontrado","Servico");
        }
        Servico save = repository.save(servico);
        return save;
    }

    public void deletar(Integer id){
        if (!repository.existsById(id)){
            throw new DataNotFoundException("Servico não encontrado","Servico");
        }
        repository.deleteById(id);
    }
}
