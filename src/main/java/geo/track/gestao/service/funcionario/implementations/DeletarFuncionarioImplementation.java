package geo.track.gestao.service.funcionario.implementations;

import geo.track.gestao.entity.repository.FuncionarioRepository;
import geo.track.gestao.service.funcionario.DeletarFuncionarioUseCase;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.exception.constraint.message.FuncionarioExceptionMessages;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarFuncionarioImplementation implements DeletarFuncionarioUseCase {
    private final FuncionarioRepository FUNCIONARIO_REPOSITORY;
    private final Log log;

    public void execute(Integer id) {
        log.info("Solicitacao para deletar funcionario ID: {}", id);
        if (!FUNCIONARIO_REPOSITORY.existsById(id)) {
            log.error("Falha ao deletar: Funcionario ID {} nao encontrado", id);
            throw new DataNotFoundException(FuncionarioExceptionMessages.FUNCIONARIO_NAO_ENCONTRADO_GENERICO, Domains.FUNCIONARIO);
        }
        FUNCIONARIO_REPOSITORY.deleteById(id);
        log.info("Funcionario ID {} deletado com sucesso", id);
    }
}

