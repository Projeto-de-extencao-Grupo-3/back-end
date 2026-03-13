package geo.track.service;

import geo.track.domain.Funcionario;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.Domains;
import geo.track.exception.constraint.message.FuncionarioExceptionMessages;
import geo.track.log.Log;
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
    private final Log log;

    public Funcionario cadastrar(Funcionario body){
        log.info("Iniciando cadastro de novo funcionário com email: {}", body.getEmail());
        if (FUNCIONARIO_REPOSITORY.existsByEmail(body.getEmail())){
            log.warn("Falha ao cadastrar: Email {} já existe no sistema", body.getEmail());
            throw new ConflictException(FuncionarioExceptionMessages.EMAIL_JA_CADASTRADO, Domains.FUNCIONARIO);
        }

        body.setSenha(PASSWORD_ENCODER.encode(body.getSenha()));
        log.info("Funcionário cadastrado com sucesso para o email: {}", body.getEmail());
        return FUNCIONARIO_REPOSITORY.save(body);
    }

    public List<Funcionario> buscarPorOficina(Integer idOficina) {
        log.info("Buscando funcionários vinculados à oficina ID: {}", idOficina);
        return FUNCIONARIO_REPOSITORY.findByFkOficina_IdOficina(idOficina);
    }

    public Funcionario buscarPorId(Integer id){
        log.info("Buscando funcionário por ID: {}", id);
        if (!FUNCIONARIO_REPOSITORY.existsByIdFuncionario(id)){
            log.error("Funcionário com ID {} não encontrado", id);
            throw new DataNotFoundException(FuncionarioExceptionMessages.FUNCIONARIO_NAO_ENCONTRADO_GENERICO, Domains.FUNCIONARIO);
        }
        return FUNCIONARIO_REPOSITORY.getByIdFuncionario(id);
    }

    public Funcionario atualizar(Integer id, Funcionario body){
        log.info("Iniciando atualização do funcionário ID: {}", id);
        body.setIdFuncionario(id);
        if (!FUNCIONARIO_REPOSITORY.existsById(id)){
            log.error("Falha na atualização: Funcionário ID {} não existe", id);
            throw new DataNotFoundException(FuncionarioExceptionMessages.FUNCIONARIO_NAO_ENCONTRADO_GENERICO, Domains.FUNCIONARIO);
        }
        return FUNCIONARIO_REPOSITORY.save(body);
    }

    public void deletar(Integer id){
        log.info("Solicitação para deletar funcionário ID: {}", id);
        if (!FUNCIONARIO_REPOSITORY.existsById(id)){
            log.error("Falha ao deletar: Funcionário ID {} não encontrado", id);
            throw new DataNotFoundException(FuncionarioExceptionMessages.FUNCIONARIO_NAO_ENCONTRADO_GENERICO, Domains.FUNCIONARIO);
        }
        FUNCIONARIO_REPOSITORY.deleteById(id);
        log.info("Funcionário ID {} deletado com sucesso", id);
    }
}
