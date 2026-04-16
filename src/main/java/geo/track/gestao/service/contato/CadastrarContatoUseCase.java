package geo.track.gestao.service.contato;

import geo.track.dto.contatos.request.RequestPostContato;
import geo.track.gestao.entity.Contato;

public interface CadastrarContatoUseCase {
    Contato execute(Integer idCliente, RequestPostContato request);
}

