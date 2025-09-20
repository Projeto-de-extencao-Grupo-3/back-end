package geo.track.service;

import geo.track.domain.Funcionarios;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.FuncionariosRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FuncionariosService {
    private final FuncionariosRepository repository;

    public FuncionariosService(FuncionariosRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Funcionarios> cadastrar(Funcionarios funcionarios){
        if (repository.existsByNome(funcionarios.getNome())){
            throw new ConflictException("Conflito de Nome","");
        }
        Funcionarios funcionarioRegistrado = repository.save(funcionarios);
        return ResponseEntity.status(201).body(funcionarioRegistrado);
    }

    public ResponseEntity<Funcionarios> buscarPorId(Integer id){
        if (!repository.existsByIdFuncionario(id)){
            throw new DataNotFoundException("Funcionario Não encontrado","");
        }
        Funcionarios getFuncionario = repository.getByIdFuncionario(id);
        return ResponseEntity.status(200).body(getFuncionario);
    }

    public ResponseEntity<Funcionarios> atualizar(Integer id, Funcionarios funcionario){
        funcionario.setIdFuncionario(id);
        if (!repository.existsById(id)){
            throw new DataNotFoundException("Funcionario não encontrado","");
        }
        Funcionarios save = repository.save(funcionario);
        return ResponseEntity.status(200).body(save);
    }

    public ResponseEntity<Void> deletar(Integer id){
        if (!repository.existsById(id)){
            throw new DataNotFoundException("Funcionario não encontrado","");
        }
        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }


}
