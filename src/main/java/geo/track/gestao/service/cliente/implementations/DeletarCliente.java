package geo.track.gestao.service.cliente.implementations;

import geo.track.gestao.entity.repository.ClienteRepository;
import geo.track.gestao.service.cliente.DeletarClienteUseCase;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.ClienteExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarCliente implements DeletarClienteUseCase {
    private final ClienteRepository CLIENTE_REPOSITORY;
    private final Log log;

    public void execute(Integer id) {
        log.info("Solicitacao para deletar cliente ID: {}", id);
        if (CLIENTE_REPOSITORY.existsById(id)) {
            CLIENTE_REPOSITORY.deleteById(id);
            log.info("Cliente ID {} deletado com sucesso", id);
            return;
        }
        log.error("Falha ao deletar: Cliente ID {} nao existe", id);
        throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID, id), Domains.CLIENTE);
    }
}

