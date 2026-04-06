package geo.track.gestao.service.funcionario.implementations;

import geo.track.gestao.entity.Funcionario;
import geo.track.gestao.entity.Oficina;
import geo.track.gestao.entity.repository.FuncionarioRepository;
import geo.track.gestao.entity.repository.OficinaRepository;
import geo.track.gestao.service.funcionario.CadastrarFuncionarioUseCase;
import geo.track.gestao.util.FuncionarioMapper;
import geo.track.dto.funcionarios.request.RequestPostFuncionario;
import geo.track.infraestructure.exception.ConflictException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.FuncionarioExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CadastrarFuncionario implements CadastrarFuncionarioUseCase {
    private final FuncionarioRepository FUNCIONARIO_REPOSITORY;
    private final OficinaRepository OFICINA_REPOSITORY;
    private final PasswordEncoder PASSWORD_ENCODER;
    private final Log log;

    public Funcionario execute(RequestPostFuncionario body) {
        log.info("Iniciando cadastro de novo funcionario com email: {}", body.getEmail());
        if (FUNCIONARIO_REPOSITORY.existsByEmail(body.getEmail())) {
            log.warn("Falha ao cadastrar: Email {} ja existe no sistema", body.getEmail());
            throw new ConflictException(FuncionarioExceptionMessages.EMAIL_JA_CADASTRADO, Domains.FUNCIONARIO);
        }

        Funcionario funcionario = FuncionarioMapper.toEntity(body);

        Oficina oficina = OFICINA_REPOSITORY.findById(body.getFkOficina())
                .orElseThrow(() -> new RuntimeException("Oficina nao encontrada com ID: " + body.getFkOficina()));

        funcionario.setFkOficina(oficina);

        funcionario.setSenha(PASSWORD_ENCODER.encode(body.getSenha()));
        log.info("Funcionario cadastrado com sucesso para o email: {}", body.getEmail());
        return FUNCIONARIO_REPOSITORY.save(funcionario);
    }
}

