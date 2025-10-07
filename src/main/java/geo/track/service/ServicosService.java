package geo.track.service;

import geo.track.domain.Servicos;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.ServicosRepository;
import org.springframework.stereotype.Service;

@Service
public class ServicosService {

    private final ServicosRepository repository;

    public ServicosService(ServicosRepository repository) {
        this.repository = repository;
    }

    public Servicos cadastrar(Servicos servicoss){
        if (repository.existsByTituloServico(servicoss.getTituloServico())){
            throw new ConflictException("Nome já existe","Servico");
        }
        Servicos servicosRegistrado = repository.save(servicoss);
        return servicosRegistrado;
    }

    public Servicos buscarPorId(Integer id){
        if (!repository.existsByIdServico(id)){
            throw new DataNotFoundException("Servico Não encontrado","Servico");
        }
        Servicos getServico = repository.getByIdServico(id);
        return getServico;
    }

    public Servicos atualizar(Integer id, Servicos servicos){
        servicos.setIdServico(id);
        if (!repository.existsById(id)){
            throw new DataNotFoundException("Servico não encontrado","Servico");
        }
        Servicos save = repository.save(servicos);
        return save;
    }

    public void deletar(Integer id){
        if (!repository.existsById(id)){
            throw new DataNotFoundException("Servico não encontrado","Servico");
        }
        repository.deleteById(id);
    }
}
