package geo.track.gestao.cliente.application.contato;

import geo.track.gestao.cliente.infraestructure.request.contato.RequestPutContato;
import geo.track.gestao.cliente.infraestructure.persistence.entity.embedded.Contato;

public interface AtualizarContatoUseCase {
    Contato execute(Integer idCliente, Integer idContato, RequestPutContato request);
}
