package geo.track.gestao.cliente.application;

import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.request.cliente.RequestPutCliente;

public interface AtualizarClienteUseCase {
    Cliente execute(RequestPutCliente request);
}

