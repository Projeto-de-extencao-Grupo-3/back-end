package geo.track.gestao.cliente.domain;

import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.persistence.ClienteRepository;
import geo.track.gestao.cliente.application.DeletarClienteUseCase;
import geo.track.gestao.cliente.application.contato.DeletarContatoUseCase;
import geo.track.gestao.cliente.application.endereco.DeletarEnderecoUseCase;
import geo.track.infraestructure.exception.DataNotFoundException;
import geo.track.infraestructure.exception.constraint.message.ClienteExceptionMessages;
import geo.track.infraestructure.exception.constraint.message.Domains;
import geo.track.infraestructure.log.Log;
import geo.track.jornada.domain.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletarCliente implements DeletarClienteUseCase {
    private final ClienteRepository CLIENTE_REPOSITORY;
    private final ClienteService CLIENTE_SERVICE;
    private final OrdemDeServicoService ORDEM_SERVICO_SERVICE;
    private final DeletarContatoUseCase DELETAR_CONTATO_USE_CASE;
    private final DeletarEnderecoUseCase DELETAR_ENDERECO_USE_CASE;
    private final Log log;

    public void execute(Integer id) {

        // Valida se o cliente possui ordens de serviço com o status diferente de FINALIZADO antes de permitir a exclusão
        if (ORDEM_SERVICO_SERVICE.existeOrdemServicoAbertaPorCliente(id)) {
            log.warn("Falha ao deletar cliente ID {}: existem ordens de serviço não finalizadas associadas", id);
            throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_PODE_SER_DELETADO_ORDENS_ABERTAS, id), Domains.CLIENTE);
        }

        log.info("Solicitacao para deletar cliente ID: {}", id);
        if (CLIENTE_REPOSITORY.existsById(id)) {
            Cliente cliente = CLIENTE_SERVICE.buscarClientePorId(id);

            cliente.getContatos().forEach(contato -> DELETAR_CONTATO_USE_CASE.execute(id, contato.getIdContato()));
            cliente.getEnderecos().forEach(endereco -> DELETAR_ENDERECO_USE_CASE.execute(endereco.getIdEndereco()));

            cliente.setAtivo(false);
            CLIENTE_REPOSITORY.save(cliente);
            log.info("Cliente ID {} deletado com sucesso", id);
            return;
        }
        log.error("Falha ao deletar: Cliente ID {} nao existe", id);
        throw new DataNotFoundException(String.format(ClienteExceptionMessages.CLIENTE_NAO_ENCONTRADO_ID, id), Domains.CLIENTE);
    }
}

