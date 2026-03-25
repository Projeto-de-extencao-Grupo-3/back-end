package geo.track.gestao.service.cliente.implementations;

import geo.track.gestao.entity.Cliente;
import geo.track.gestao.entity.repository.ClienteRepository;
import geo.track.gestao.service.cliente.AlterarEmailClienteUseCase;
import geo.track.dto.clientes.request.RequestPatchEmail;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlterarEmailClienteImplementation implements AlterarEmailClienteUseCase {
    private final ClienteRepository CLIENTE_REPOSITORY;
    private final ClienteService CLIENTE_SERVICE;
    private final Log log;

    public Cliente execute(RequestPatchEmail body) {
        log.info("Atualizando e-mail do cliente ID: {}", body.getIdCliente());
        Cliente cliente = CLIENTE_SERVICE.findClienteById(body.getIdCliente());
        cliente.setIdCliente(body.getIdCliente());
        cliente.setEmail(body.getEmail());

        log.info("E-mail do cliente ID {} atualizado com sucesso", body.getIdCliente());
        return CLIENTE_REPOSITORY.save(cliente);
    }
}

