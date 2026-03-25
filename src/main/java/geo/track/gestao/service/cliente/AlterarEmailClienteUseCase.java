package geo.track.gestao.service.cliente;

import geo.track.gestao.entity.Cliente;
import geo.track.dto.clientes.request.RequestPatchEmail;

public interface AlterarEmailClienteUseCase {
    Cliente execute(RequestPatchEmail request);
}

