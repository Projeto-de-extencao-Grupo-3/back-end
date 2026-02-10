package geo.track.service;

import geo.track.domain.Funcionario;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository repository;
    private final PasswordEncoder passwordEncoder;


    public Funcionario cadastrar(Funcionario body){
        if (repository.existsByEmail(body.getEmail())){
            throw new ConflictException("Funcionário já existente com este email!","Funcionario");
        }

        body.setSenha(passwordEncoder.encode(body.getSenha()));
        return repository.save(body);
    }

    public List<Funcionario> buscarPorOficina(Integer idOficina) {
        return repository.findByFkOficina_IdOficina(idOficina);
    }

    public Funcionario buscarPorId(Integer id){
        if (!repository.existsByIdFuncionario(id)){
            throw new DataNotFoundException("Funcionario não encontrado","Funcionario");
        }
        return repository.getByIdFuncionario(id);
    }

    public Funcionario atualizar(Integer id, Funcionario funcionario){
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
