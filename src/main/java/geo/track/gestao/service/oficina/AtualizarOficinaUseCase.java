package geo.track.gestao.service.oficina;

import geo.track.gestao.entity.Oficina;
import geo.track.dto.oficinas.request.RequestPutOficina;

public interface AtualizarOficinaUseCase {
    Oficina execute(RequestPutOficina request);
}

