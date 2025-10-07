package geo.track.service;

import geo.track.domain.Funcionarios;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.FuncionariosRepository;
import org.springframework.stereotype.Service;

@Service
public class FuncionariosService {
    private final FuncionariosRepository repository;

    public FuncionariosService(FuncionariosRepository repository) {
        this.repository = repository;
    }

    public Funcionarios cadastrar(Funcionarios funcionarios){
        if (repository.existsByNome(funcionarios.getNome())){
            throw new ConflictException("Nome já existe","Funcionario");
        }
        Funcionarios funcionarioRegistrado = repository.save(funcionarios);
        return funcionarioRegistrado;
    }

    public Funcionarios buscarPorId(Integer id){
        if (!repository.existsByIdFuncionario(id)){
            throw new DataNotFoundException("Funcionario Não encontrado","Funcionario");
        }
        Funcionarios getFuncionario = repository.getByIdFuncionario(id);
        return getFuncionario;
    }

    public Funcionarios atualizar(Integer id, Funcionarios funcionario){
        funcionario.setIdFuncionario(id);
        if (!repository.existsById(id)){
            throw new DataNotFoundException("Funcionario não encontrado","Funcionario");
        }
        Funcionarios save = repository.save(funcionario);
        return save;
    }

    public void deletar(Integer id){
        if (!repository.existsById(id)){
            throw new DataNotFoundException("Funcionario não encontrado","Funcionario");
        }
        repository.deleteById(id);
    }

}
