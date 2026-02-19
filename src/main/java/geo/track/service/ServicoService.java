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

    private final ServicoRepository SERVICO_REPOSITORY;

    public Servico cadastrar(Servico servicoss){
        if (SERVICO_REPOSITORY.existsByTituloServico(servicoss.getTituloServico())){
            throw new ConflictException("Nome já existe","Servico");
        }
        Servico servicoRegistrado = SERVICO_REPOSITORY.save(servicoss);
        return servicoRegistrado;
    }

    public Servico buscarPorId(Integer id){
        if (!SERVICO_REPOSITORY.existsByIdServico(id)){
            throw new DataNotFoundException("Servico Não encontrado","Servico");
        }
        Servico getServico = SERVICO_REPOSITORY.getByIdServico(id);
        return getServico;
    }

    public Servico atualizar(Integer id, Servico servico){
        servico.setIdServico(id);
        if (!SERVICO_REPOSITORY.existsById(id)){
            throw new DataNotFoundException("Servico não encontrado","Servico");
        }
        Servico save = SERVICO_REPOSITORY.save(servico);
        return save;
    }

    public void deletar(Integer id){
        if (!SERVICO_REPOSITORY.existsById(id)){
            throw new DataNotFoundException("Servico não encontrado","Servico");
        }
        SERVICO_REPOSITORY.deleteById(id);
    }
}
