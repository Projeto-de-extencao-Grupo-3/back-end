package geo.track.gestao.funcionario.domain;

import geo.track.gestao.funcionario.infraestructure.persistence.entity.Funcionario;
import geo.track.gestao.funcionario.infraestructure.persistence.FuncionarioRepository;
import geo.track.gestao.funcionario.application.AtualizarFuncionarioUseCase;
import geo.track.gestao.funcionario.infraestructure.FuncionarioMapper;
import geo.track.gestao.funcionario.infraestructure.request.RequestPutFuncionario;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.FuncionarioExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtualizarFuncionario implements AtualizarFuncionarioUseCase {
    private final FuncionarioRepository FUNCIONARIO_REPOSITORY;
    private final Log log;

    public Funcionario execute(RequestPutFuncionario body) {
        log.info("Iniciando atualizacao do funcionario ID: {}", body.getId());

        if (!FUNCIONARIO_REPOSITORY.existsById(body.getId())) {
            log.error("Falha na atualizacao: Funcionario ID {} nao existe", body.getId());
            throw new DataNotFoundException(FuncionarioExceptionMessages.FUNCIONARIO_NAO_ENCONTRADO_GENERICO, Domains.FUNCIONARIO);
        }

        Funcionario funcionario = FUNCIONARIO_REPOSITORY.getByIdFuncionario(body.getId());

        funcionario = FuncionarioMapper.toEntityUpdate(funcionario, body);

        log.info("Funcionario ID {} atualizado com sucesso", body.getId());
        return FUNCIONARIO_REPOSITORY.save(funcionario);
    }
}

