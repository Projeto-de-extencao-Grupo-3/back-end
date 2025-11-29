package geo.track.service;

import geo.track.domain.Funcionarios;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.FuncionariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionariosService {
    private final FuncionariosRepository repository;

    public Funcionarios cadastrar(Funcionarios funcionarios){
        if (repository.existsByNome(funcionarios.getNome())){
            throw new ConflictException("Nome já existe","Funcionario");
        }
        return repository.save(funcionarios);
    }

    public List<Funcionarios> buscarPorOficina(Integer idOficina) {
        return repository.findByFkOficina_IdOficina(idOficina);
    }

    public Funcionarios buscarPorId(Integer id){
        if (!repository.existsByIdFuncionario(id)){
            throw new DataNotFoundException("Funcionario Não encontrado","Funcionario");
        }
        return repository.getByIdFuncionario(id);
    }

    public Funcionarios atualizar(Integer id, Funcionarios funcionario){
        funcionario.setIdFuncionario(id);
        if (!repository.existsById(id)){
            throw new DataNotFoundException("Funcionario não encontrado","Funcionario");
        }
        return repository.save(funcionario);
    }

    public void deletar(Integer id){
        if (!repository.existsById(id)){
            throw new DataNotFoundException("Funcionario não encontrado","Funcionario");
        }
        repository.deleteById(id);
    }

}
