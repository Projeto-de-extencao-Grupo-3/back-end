package geo.track.gestao.service.cliente;

import geo.track.gestao.entity.Cliente;
import geo.track.dto.clientes.request.RequestPatchTelefone;

public interface AlterarTelefoneClienteUseCase {
    Cliente execute(RequestPatchTelefone request);
}

