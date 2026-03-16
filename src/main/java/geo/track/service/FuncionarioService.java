package geo.track.service;

import geo.track.domain.Funcionario;
import geo.track.domain.Oficina;
import geo.track.dto.funcionarios.request.RequestPostFuncionario;
import geo.track.dto.funcionarios.request.RequestPutFuncionario;
import geo.track.exception.ConflictException;
import geo.track.exception.DataNotFoundException;
import geo.track.exception.constraint.message.Domains;
import geo.track.exception.constraint.message.FuncionarioExceptionMessages;
import geo.track.log.Log;
import geo.track.mapper.FuncionarioMapper;
import geo.track.repository.FuncionarioRepository;
import geo.track.repository.OficinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository FUNCIONARIO_REPOSITORY;
    private final OficinaRepository OFICINA_RESPOSITORY;
    private final PasswordEncoder PASSWORD_ENCODER;
    private final Log log;

    public Funcionario cadastrar(RequestPostFuncionario body){
        log.info("Iniciando cadastro de novo funcionário com email: {}", body.getEmail());
        if (FUNCIONARIO_REPOSITORY.existsByEmail(body.getEmail())){
            log.warn("Falha ao cadastrar: Email {} já existe no sistema", body.getEmail());
            throw new ConflictException(FuncionarioExceptionMessages.EMAIL_JA_CADASTRADO, Domains.FUNCIONARIO);
        }

        Funcionario funcionario = FuncionarioMapper.toEntity(body);

        Oficina oficina = OFICINA_RESPOSITORY.findById(body.getFkOficina())
                .orElseThrow(() -> new RuntimeException("Oficina não encontrada com ID: " + body.getFkOficina()));

        // ASSOCIAÇÃO: Agora o objeto funcionário sabe a qual oficina pertence
        funcionario.setFkOficina(oficina);

        funcionario.setSenha(PASSWORD_ENCODER.encode(body.getSenha()));
        log.info("Funcionário cadastrado com sucesso para o email: {}", body.getEmail());
        return FUNCIONARIO_REPOSITORY.save(funcionario);
    }

    public List<Funcionario> listar(){
        log.info("Buscando lista completa de funcionários no banco de dados.");
        return FUNCIONARIO_REPOSITORY.findAll();
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

    public Funcionario atualizar(RequestPutFuncionario body){
        log.info("Iniciando atualização do funcionário ID: {}", body.getId());
        Funcionario funcionario = this.buscarPorId(body.getId());

        funcionario = FuncionarioMapper.toEntityUpdate(funcionario, body);

        if (!FUNCIONARIO_REPOSITORY.existsById(body.getId())){
            log.error("Falha na atualização: Funcionário ID {} não existe", body.getId());
            throw new DataNotFoundException(FuncionarioExceptionMessages.FUNCIONARIO_NAO_ENCONTRADO_GENERICO, Domains.FUNCIONARIO);
        }

        log.info("Funcionário ID {} atualizado com sucesso", body.getId());
        return FUNCIONARIO_REPOSITORY.save(funcionario);
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
