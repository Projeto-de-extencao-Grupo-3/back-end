package geo.track.gestao.cliente.domain;

import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.persistence.ClienteRepository;
import geo.track.gestao.cliente.application.AtualizarClienteUseCase;
import geo.track.gestao.cliente.infraestructure.persistence.entity.TipoCliente;
import geo.track.gestao.cliente.infraestructure.request.cliente.RequestPutCliente;
import geo.track.gestao.cliente.infraestructure.ClientesMapper;
import geo.track.infraestructure.exception.BadBusinessRuleException;
import geo.track.infraestructure.log.Log;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class AtualizarCliente implements AtualizarClienteUseCase {
    private final ClienteRepository CLIENTE_REPOSITORY;
    private final ClienteService CLIENTE_SERVICE;
    private final Log log;

    public Cliente execute(RequestPutCliente body) {
        log.info("Atualizando dados completos do cliente ID: {}", body.getIdCliente());
        Cliente cliente = CLIENTE_SERVICE.buscarClientePorId(body.getIdCliente());

        Cliente clienteUpdate = ClientesMapper.updateEntity(cliente, body);

        log.info("Dados do cliente ID {} atualizados com sucesso", body.getIdCliente());
        return CLIENTE_REPOSITORY.save(clienteUpdate);
    }
}

