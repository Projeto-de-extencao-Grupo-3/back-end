package geo.track.gestao.service.cliente.implementations;

import geo.track.gestao.entity.Cliente;
import geo.track.gestao.entity.repository.ClienteRepository;
import geo.track.gestao.service.cliente.AlterarTelefoneClienteUseCase;
import geo.track.dto.clientes.request.RequestPatchTelefone;
import geo.track.infraestructure.log.Log;
import geo.track.gestao.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlterarTelefoneCliente implements AlterarTelefoneClienteUseCase {
    private final ClienteRepository CLIENTE_REPOSITORY;
    private final ClienteService CLIENTE_SERVICE;
    private final Log log;

    public Cliente execute(RequestPatchTelefone body) {
        log.info("Atualizando telefone do cliente ID: {}", body.getIdCliente());
        Cliente cliente = CLIENTE_SERVICE.bucarClientePorId(body.getIdCliente());
        cliente.setIdCliente(body.getIdCliente());
        cliente.setTelefone(body.getTelefone());

        log.info("Telefone do cliente ID {} atualizado com sucesso", body.getIdCliente());
        return CLIENTE_REPOSITORY.save(cliente);
    }
}

