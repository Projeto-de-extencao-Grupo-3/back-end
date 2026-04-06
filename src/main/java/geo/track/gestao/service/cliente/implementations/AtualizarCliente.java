package geo.track.gestao.service.cliente.implementations;

import geo.track.gestao.entity.Cliente;
import geo.track.gestao.entity.repository.ClienteRepository;
import geo.track.gestao.service.cliente.AtualizarClienteUseCase;
import geo.track.dto.clientes.request.RequestPutCliente;
import geo.track.gestao.util.ClientesMapper;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtualizarCliente implements AtualizarClienteUseCase {
    private final ClienteRepository CLIENTE_REPOSITORY;
    private final ClienteService CLIENTE_SERVICE;
    private final Log log;

    public Cliente execute(RequestPutCliente body) {
        log.info("Atualizando dados completos do cliente ID: {}", body.getIdCliente());

        Cliente cliente = CLIENTE_SERVICE.findClienteById(body.getIdCliente());

        Cliente clienteUpdate = ClientesMapper.updateEntity(cliente, body);

        log.info("Dados do cliente ID {} atualizados com sucesso", body.getIdCliente());
        return CLIENTE_REPOSITORY.save(clienteUpdate);
    }
}

