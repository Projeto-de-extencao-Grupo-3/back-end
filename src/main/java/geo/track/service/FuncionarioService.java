package geo.track.service;

import geo.track.domain.Cliente;
import geo.track.domain.Funcionario;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.EnumDomains;
import geo.track.exception.constraint.message.FuncionarioExceptionMessages;
import geo.track.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository FUNCIONARIO_REPOSITORY;
    private final PasswordEncoder PASSWORD_ENCODER;


    public Funcionario cadastrar(Funcionario body){
        if (FUNCIONARIO_REPOSITORY.existsByEmail(body.getEmail())){
            throw new ConflictException(FuncionarioExceptionMessages.EMAIL_JA_CADASTRADO, EnumDomains.FUNCIONARIO);
        }

        body.setSenha(PASSWORD_ENCODER.encode(body.getSenha()));
        return FUNCIONARIO_REPOSITORY.save(body);
    }

    public List<Funcionario> findFuncionarios(){
        return FUNCIONARIO_REPOSITORY.findAll();
    }

    public List<Funcionario> buscarPorOficina(Integer idOficina) {
        return FUNCIONARIO_REPOSITORY.findByFkOficina_IdOficina(idOficina);
    }

    public Funcionario buscarPorId(Integer id){
        if (!FUNCIONARIO_REPOSITORY.existsByIdFuncionario(id)){
            throw new DataNotFoundException(FuncionarioExceptionMessages.FUNCIONARIO_NAO_ENCONTRADO_GENERICO,EnumDomains.FUNCIONARIO);
        }
        return FUNCIONARIO_REPOSITORY.getByIdFuncionario(id);
    }

    public Funcionario atualizar(Integer id, Funcionario funcionario){
        funcionario.setIdFuncionario(id);
        if (!FUNCIONARIO_REPOSITORY.existsById(id)){
            throw new DataNotFoundException(FuncionarioExceptionMessages.FUNCIONARIO_NAO_ENCONTRADO_GENERICO,EnumDomains.FUNCIONARIO);
        }
        return FUNCIONARIO_REPOSITORY.save(funcionario);
    }

    public void deletar(Integer id){
        if (!FUNCIONARIO_REPOSITORY.existsById(id)){
            throw new DataNotFoundException(FuncionarioExceptionMessages.FUNCIONARIO_NAO_ENCONTRADO_GENERICO,EnumDomains.FUNCIONARIO);
        }
        FUNCIONARIO_REPOSITORY.deleteById(id);
    }

}
