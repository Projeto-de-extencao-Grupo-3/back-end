package geo.track.gestao.cliente.application;

import geo.track.gestao.cliente.infraestructure.persistence.entity.Cliente;
import geo.track.gestao.cliente.infraestructure.request.cliente.RequestPostCliente;

public interface CadastrarClienteUseCase {
    Cliente execute(RequestPostCliente request);
}

