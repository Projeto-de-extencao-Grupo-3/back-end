package geo.track.gestao.service.contato;

import geo.track.dto.contatos.request.RequestPutContato;
import geo.track.gestao.entity.Contato;

public interface AtualizarContatoUseCase {
    Contato execute(Integer idCliente, Integer idContato, RequestPutContato request);
}
