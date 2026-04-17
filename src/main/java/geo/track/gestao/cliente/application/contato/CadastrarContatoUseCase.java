package geo.track.gestao.cliente.application.contato;

import geo.track.gestao.cliente.infraestructure.request.contato.RequestPostContato;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Contato;

public interface CadastrarContatoUseCase {
    Contato execute(Integer idCliente, RequestPostContato request);
}

