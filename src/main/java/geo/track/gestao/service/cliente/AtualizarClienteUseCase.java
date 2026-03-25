package geo.track.gestao.service.cliente;

import geo.track.gestao.entity.Cliente;
import geo.track.dto.clientes.request.RequestPutCliente;

public interface AtualizarClienteUseCase {
    Cliente execute(RequestPutCliente request);
}

