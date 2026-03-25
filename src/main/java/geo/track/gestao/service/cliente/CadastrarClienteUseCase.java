package geo.track.gestao.service.cliente;

import geo.track.gestao.entity.Cliente;
import geo.track.dto.clientes.request.RequestPostCliente;

public interface CadastrarClienteUseCase {
    Cliente execute(RequestPostCliente request);
}

